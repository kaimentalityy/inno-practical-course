import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.sales.*;
import store.service.OrderAnalysisService;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderAnalysisServiceTest {

    private List<Order> orders;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;

    @BeforeEach
    void setUp() {
        customer1 = new Customer("C1", "John Doe", "john@email.com",
                LocalDateTime.now(), 30, "New York");
        customer2 = new Customer("C2", "Jane Smith", "jane@email.com",
                LocalDateTime.now(), 25, "Los Angeles");
        customer3 = new Customer("C3", "Bob Johnson", "bob@email.com",
                LocalDateTime.now(), 35, "Chicago");
        customer4 = new Customer("C4", "Empty City", "empty@email.com",
                LocalDateTime.now(), 40, "");

        Order order1 = new Order("O1", LocalDateTime.now().minusDays(10), customer1,
                Arrays.asList(
                        new OrderItem("iPhone 13", 2, 1000.0, Category.ELECTRONICS),
                        new OrderItem("T-Shirt", 1, 50.0, Category.CLOTHING)
                ), OrderStatus.DELIVERED);

        Order order2 = new Order("O2", LocalDateTime.now().minusDays(5), customer2,
                Arrays.asList(
                        new OrderItem("Book", 1, 25.0, Category.BOOKS),
                        new OrderItem("T-Shirt", 2, 50.0, Category.CLOTHING)
                ), OrderStatus.PROCESSING);

        Order order3 = new Order("O3", LocalDateTime.now().minusDays(3), customer3,
                Collections.singletonList(
                        new OrderItem("T-Shirt", 3, 20.0, Category.CLOTHING)
                ), OrderStatus.DELIVERED);

        Order order4 = new Order("O4", LocalDateTime.now().minusDays(1), customer4,
                Collections.singletonList(
                        new OrderItem("Headphones", 1, 200.0, Category.ELECTRONICS)
                ), OrderStatus.CANCELLED);

        Order order5 = new Order("O5", LocalDateTime.now(), customer1,
                Collections.singletonList(
                        new OrderItem("Laptop", 1, 1500.0, Category.ELECTRONICS)
                ), OrderStatus.SHIPPED);

        Order order6 = new Order("O6", LocalDateTime.now().minusDays(2), customer1,
                Collections.singletonList(
                        new OrderItem("iPhone 13", 1, 1000.0, Category.ELECTRONICS)
                ), OrderStatus.NEW);

        orders = Arrays.asList(order1, order2, order3, order4, order5, order6);
    }

    @Test
    void testExtractDistinctCustomerCities_WithValidData() {
        Set<String> cities = OrderAnalysisService.extractDistinctCustomerCities(orders);

        assertEquals(3, cities.size());
        assertTrue(cities.contains("New York"));
        assertTrue(cities.contains("Los Angeles"));
        assertTrue(cities.contains("Chicago"));
        assertFalse(cities.contains(""));
    }

    @Test
    void testExtractDistinctCustomerCities_WithNullCustomer() {
        Order orderWithNullCustomer = new Order("O7", LocalDateTime.now(), null,
                Collections.singletonList(new OrderItem("Test", 1, 10.0, Category.BOOKS)),
                OrderStatus.NEW);

        List<Order> ordersWithNull = new ArrayList<>(orders);
        ordersWithNull.add(orderWithNullCustomer);

        Set<String> cities = OrderAnalysisService.extractDistinctCustomerCities(ordersWithNull);

        assertEquals(3, cities.size());
    }

    @Test
    void testExtractDistinctCustomerCities_WithEmptyList() {
        Set<String> cities = OrderAnalysisService.extractDistinctCustomerCities(Collections.emptyList());
        assertTrue(cities.isEmpty());
    }

    @Test
    void testExtractDistinctCustomerCities_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> OrderAnalysisService.extractDistinctCustomerCities(null));
    }

    @Test
    void testCalculateTotalDeliveredRevenue_WithDeliveredOrders() {
        double totalRevenue = OrderAnalysisService.calculateTotalDeliveredRevenue(orders);

        // Order1: (2 * 1000) + (1 * 50) = 2050
        // Order3: (3 * 20) = 60
        // Total: 2050 + 60 = 2110
        assertEquals(2110.0, totalRevenue, 0.001);
    }

    @Test
    void testCalculateTotalDeliveredRevenue_WithNoDeliveredOrders() {
        List<Order> nonDeliveredOrders = orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.DELIVERED)
                .toList();

        double totalRevenue = OrderAnalysisService.calculateTotalDeliveredRevenue(nonDeliveredOrders);
        assertEquals(0.0, totalRevenue, 0.001);
    }

    @Test
    void testCalculateTotalDeliveredRevenue_WithEmptyList() {
        double totalRevenue = OrderAnalysisService.calculateTotalDeliveredRevenue(Collections.emptyList());
        assertEquals(0.0, totalRevenue, 0.001);
    }

    @Test
    void testIdentifyTopSellingProduct_WithValidData() {
        String topProduct = OrderAnalysisService.identifyTopSellingProduct(orders);
        assertEquals("T-Shirt", topProduct);
    }

    @Test
    void testIdentifyTopSellingProduct_WithOnlyCancelledOrders() {
        List<Order> cancelledOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CANCELLED)
                .toList();

        String topProduct = OrderAnalysisService.identifyTopSellingProduct(cancelledOrders);
        assertEquals("No products found", topProduct);
    }

    @Test
    void testIdentifyTopSellingProduct_WithEmptyList() {
        String topProduct = OrderAnalysisService.identifyTopSellingProduct(Collections.emptyList());
        assertEquals("No products found", topProduct);
    }

    @Test
    void testComputeAverageOrderValue_WithDeliveredOrders() {
        double averageOrderValue = OrderAnalysisService.computeAverageOrderValue(orders);

        // Order1 total: 2050
        // Order3 total: 60
        // Average: (2050 + 60) / 2 = 1055
        assertEquals(1055.0, averageOrderValue, 0.001);
    }

    @Test
    void testComputeAverageOrderValue_WithNoDeliveredOrders() {
        List<Order> nonDeliveredOrders = orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.DELIVERED)
                .toList();

        double averageOrderValue = OrderAnalysisService.computeAverageOrderValue(nonDeliveredOrders);
        assertEquals(0.0, averageOrderValue, 0.001);
    }

    @Test
    void testFindFrequentCustomers_WithThreshold() {
       List<Customer> frequentCustomers = OrderAnalysisService.findFrequentCustomers(orders, 2);

        assertEquals(1, frequentCustomers.size());
        assertTrue(frequentCustomers.contains(customer1));
    }

    @Test
    void testFindFrequentCustomers_WithHighThreshold() {
        List<Customer> frequentCustomers = OrderAnalysisService.findFrequentCustomers(orders, 5);
        assertTrue(frequentCustomers.isEmpty());
    }

    @Test
    void testFindFrequentCustomers_WithNullCustomers() {
        Order orderWithNullCustomer = new Order("O7", LocalDateTime.now(), null,
                Collections.singletonList(new OrderItem("Test", 1, 10.0, Category.BOOKS)),
                OrderStatus.NEW);

        List<Order> ordersWithNull = new ArrayList<>(orders);
        ordersWithNull.add(orderWithNullCustomer);

        List<Customer> frequentCustomers = OrderAnalysisService.findFrequentCustomers(ordersWithNull, 1);
        assertTrue(frequentCustomers.stream().noneMatch(Objects::isNull));
    }

    @Test
    void testFindCustomersWithMoreThanFiveOrders() {
        List<Customer> customers = OrderAnalysisService.findCustomersWithMoreThanFiveOrders(orders);
        assertTrue(customers.isEmpty());
    }

    @Test
    void testAnalyzeRevenueByCategory_WithDeliveredOrders() {
        Map<Category, Double> revenueByCategory = OrderAnalysisService.analyzeRevenueByCategory(orders);
        assertEquals(2000.0, revenueByCategory.get(Category.ELECTRONICS), 0.001);
        assertEquals(110.0, revenueByCategory.get(Category.CLOTHING), 0.001);
        assertNull(revenueByCategory.get(Category.BOOKS)); // Only processing order has books
    }

    @Test
    void testAnalyzeRevenueByCategory_WithNoDeliveredOrders() {
        List<Order> nonDeliveredOrders = orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.DELIVERED)
                .toList();

        Map<Category, Double> revenueByCategory = OrderAnalysisService.analyzeRevenueByCategory(nonDeliveredOrders);
        assertTrue(revenueByCategory.isEmpty());
    }

    @Test
    void testAllMethods_WithNullOrdersList() {
        assertThrows(NullPointerException.class, () -> OrderAnalysisService.extractDistinctCustomerCities(null));
        assertThrows(NullPointerException.class, () -> OrderAnalysisService.calculateTotalDeliveredRevenue(null));
        assertThrows(NullPointerException.class, () -> OrderAnalysisService.identifyTopSellingProduct(null));
        assertThrows(NullPointerException.class, () -> OrderAnalysisService.computeAverageOrderValue(null));
        assertThrows(NullPointerException.class, () -> OrderAnalysisService.findFrequentCustomers(null, 1));
        assertThrows(NullPointerException.class, () -> OrderAnalysisService.analyzeRevenueByCategory(null));
    }
}

