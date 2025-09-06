package store.service;

import store.sales.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for analyzing e-commerce order data and generating
 * key business performance metrics using Stream API operations.
 */
public class OrderAnalysisService {

    /**
     * Retrieves distinct cities from which customers have placed orders.
     * Filters out null customers and null/empty city names.
     *
     * @param orders the list of orders to analyze
     * @return a {@link Set} of unique city names, empty set if no valid cities found
     * @throws NullPointerException if orders list is null
     */
    public static Set<String> extractDistinctCustomerCities(List<Order> orders) {
        Objects.requireNonNull(orders, "Orders list cannot be null");

        return orders.stream()
                .map(Order::getCustomer)
                .filter(Objects::nonNull)
                .map(Customer::getCity)
                .filter(Objects::nonNull)
                .filter(city -> !city.trim().isEmpty())
                .collect(Collectors.toSet());
    }

    /**
     * Computes total revenue generated from all successfully delivered orders.
     * Revenue is calculated as the sum of (price * quantity) for all items in delivered orders.
     *
     * @param orders the list of orders to analyze
     * @return the total revenue as a double, 0.0 if no delivered orders found
     * @throws NullPointerException if orders list is null
     */
    public static double calculateTotalDeliveredRevenue(List<Order> orders) {
        Objects.requireNonNull(orders, "Orders list cannot be null");

        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    /**
     * Identifies the best-selling product based on total quantity sold across non-cancelled orders.
     *
     * @param orders the list of orders to analyze
     * @return the name of the top-selling product, or "No products found" if there are no orders
     * @throws NullPointerException if orders list is null
     */
    public static String identifyTopSellingProduct(List<Order> orders) {
        Objects.requireNonNull(orders, "Orders list cannot be null");

        Map<String, Integer> productSales = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED
                        || order.getStatus() == OrderStatus.SHIPPED)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getProductName,
                        Collectors.summingInt(OrderItem::getQuantity)
                ));

        return productSales.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No products found");
    }


    /**
     * Calculates the average order value for successfully delivered orders.
     * Order value is computed as the sum of (price * quantity) for all items in the order.
     *
     * @param orders the list of orders to analyze
     * @return the average order value as a double, or 0.0 if there are no delivered orders
     * @throws NullPointerException if orders list is null
     */
    public static double computeAverageOrderValue(List<Order> orders) {
        Objects.requireNonNull(orders, "Orders list cannot be null");

        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getPrice())
                        .sum())
                .average()
                .orElse(0.0);
    }

    /**
     * Finds customers who have placed more than a specified number of orders.
     * Filters out orders with null customers.
     *
     * @param orders the list of orders to analyze
     * @param minOrderCount the minimum number of orders required (exclusive)
     * @return a {@link List} of {@link Customer} objects who meet the criteria,
     *         empty list if no customers meet the criteria
     * @throws NullPointerException if orders list is null
     */
    public static List<Customer> findFrequentCustomers(List<Order> orders, int minOrderCount) {
        Objects.requireNonNull(orders, "Orders list cannot be null");

        return orders.stream()
                .filter(order -> order.getCustomer() != null)
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED
                        || order.getStatus() == OrderStatus.SHIPPED)
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= minOrderCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Finds customers who have placed more than 5 orders (convenience method).
     *
     * @param orders the list of orders to analyze
     * @return a {@link List} of {@link Customer} objects with more than 5 orders
     * @throws NullPointerException if orders list is null
     */
    public static List<Customer> findCustomersWithMoreThanFiveOrders(List<Order> orders) {
        return findFrequentCustomers(orders, 5);
    }
}
