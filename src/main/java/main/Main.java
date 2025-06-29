package main;

import bank.Bank;
import shop.OnlineShop;
import shop.ShopItem;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Person alice = new Person("Alice");
        Person bob = new Person("Bob");

        bank.registerCustomer(alice, 50.0);
        bank.registerCustomer(bob, 20.0);

        OnlineShop shop = new OnlineShop(bank, bob);

        ShopItem spezi = shop.getAvailableItems().stream()
                .filter(item -> item.getName().equals("Spezi"))
                .findFirst().orElseThrow();

        ShopItem sprite = shop.getAvailableItems().stream()
                .filter(item -> item.getName().equals("Sprite"))
                .findFirst().orElseThrow();

        shop.addToCart(alice, spezi);
        shop.addToCart(alice, sprite);

        shop.placeOrder(alice);
    }
}
