package shop;

import bank.Bank;
import main.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OnlineShopTest {

    private Bank bank;
    private OnlineShop shop;
    private Person alice;
    private Person bob;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        alice = new Person("Alice");
        bob = new Person("Bob");

        bank.registerCustomer(alice, 50.0);
        bank.registerCustomer(bob, 20.0);

        shop = new OnlineShop(bank, bob);
    }

    @Test
    public void testOnlineShopFlow() {
        ShopItem spezi = getItemByName("Spezi");
        ShopItem sprite = getItemByName("Sprite");

        shop.addToCart(alice, spezi);
        shop.addToCart(alice, sprite);

        boolean success = shop.placeOrder(alice);
        assertTrue(success, "Order should succeed");

        double total = spezi.getPrice() + sprite.getPrice();
        assertEquals(50.0 - total, bank.getBalance(alice), 0.01, "Alice should be charged for both items");
        assertEquals(20.0 + total, bank.getBalance(bob), 0.01, "Bob should receive the money");
    }

    @Test
    public void testAddToCartAndPlaceOrder() {
        ShopItem spezi = getItemByName("Spezi");
        ShopItem cola = getItemByName("Cola");

        shop.addToCart(alice, spezi);
        shop.addToCart(alice, cola);

        boolean success = shop.placeOrder(alice);
        assertTrue(success, "Placing order should succeed");

        double total = spezi.getPrice() + cola.getPrice();
        assertEquals(50.0 - total, bank.getBalance(alice), 0.01);
        assertEquals(20.0 + total, bank.getBalance(bob), 0.01);
    }

    @Test
    public void testPlaceOrderFailsIfNotEnoughMoney() {
        ShopItem cola = getItemByName("Cola");
        ShopItem sprite = getItemByName("Sprite");

        shop.addToCart(alice, cola);
        shop.addToCart(alice, sprite);
        shop.addToCart(alice, cola);

        boolean result = shop.placeOrder(alice);
        assertFalse(result, "Should fail if total > 50");

        assertEquals(50.0, bank.getBalance(alice), 0.01);
        assertEquals(20.0, bank.getBalance(bob), 0.01);
    }

    @Test
    public void testAddToCartFailsIfItemNotInShop() {
        ShopItem fakeItem = new ShopItem("Nonexistent", 99.99);
        int initialCartSize = shop.getAvailableItems().size();

        shop.addToCart(alice, fakeItem);

        assertEquals(initialCartSize, shop.getAvailableItems().size(), "Fake item should not be added to shop");

        boolean success = shop.placeOrder(alice);
        assertFalse(success, "Should not be able to place order with an invalid item");
    }

    private ShopItem getItemByName(String name) {
        return shop.getAvailableItems().stream()
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found: " + name));
    }
}
