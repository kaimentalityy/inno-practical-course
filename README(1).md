# Order Analysis Service

This project provides an **Order Analysis Service** for analyzing e-commerce order data using **Java Streams**.  
It includes functionalities such as revenue calculation, customer analysis, and product sales tracking, with a full **JUnit 5 test suite** for validation.

---

## 📂 Project Structure

```
src/
 ├── main/
 │   ├── java/
 │   │   ├── store/sales/
 │   │   │   ├── Customer.java
 │   │   │   ├── Order.java
 │   │   │   ├── OrderItem.java
 │   │   │   ├── Category.java
 │   │   │   └── OrderStatus.java
 │   │   └── store/service/
 │   │       └── OrderAnalysisService.java
 ├── test/
 │   ├── java/
 │   │   └── OrderAnalysisServiceTest.java
```

---

## 🚀 Features

- **Distinct Customer Cities**  
  Extracts unique cities from customer data, ignoring empty or null values.

- **Delivered Revenue Calculation**  
  Computes total revenue from successfully delivered orders.

- **Top Selling Product Identification**  
  Finds the product with the highest total sales quantity.

- **Average Order Value**  
  Calculates the average order value across delivered orders.

- **Frequent Customers Analysis**  
  Finds customers who placed more than a given number of orders.

- **Revenue by Category**  
  Summarizes total revenue across product categories.

---

## 🛠 Technologies

- **Java 17+** (Streams API, Collections)
- **JUnit 5** (Testing Framework)
- **Maven** (Dependency Management & Build)

---

## 📦 Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/order-analysis-service.git
   cd order-analysis-service
   ```

2. **Open in IntelliJ IDEA** or any Java IDE.

3. **Ensure JUnit 5** is in your Maven `pom.xml` dependencies:
   ```xml
   <dependency>
       <groupId>org.junit.jupiter</groupId>
       <artifactId>junit-jupiter</artifactId>
       <version>5.9.3</version>
       <scope>test</scope>
   </dependency>
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

---

## 🧪 Running Tests

Execute all tests using Maven:

```bash
mvn test
```

Or run the `OrderAnalysisServiceTest` class directly in your IDE.

The test suite includes:
- Distinct city extraction
- Total delivered revenue calculation
- Top-selling product detection
- Average order value computation
- Frequent customer analysis
- Revenue by category checks
- Null handling scenarios

---

## 📊 Example Usage

```java
List<Order> orders = ... // fetch or create orders
double revenue = OrderAnalysisService.calculateTotalDeliveredRevenue(orders);
System.out.println("Total Delivered Revenue: " + revenue);

String topProduct = OrderAnalysisService.identifyTopSellingProduct(orders);
System.out.println("Top Selling Product: " + topProduct);
```

---

## ✅ Test Coverage Highlights

- **Null Safety** → Methods throw `NullPointerException` for null inputs.
- **Edge Cases** → Empty lists, null customers, no delivered orders.
- **Accuracy** → Revenue and average values validated with precision.

---

## 📝 License

This project is licensed under the **MIT License**.  
You are free to use, modify, and distribute it with attribution.

---

## 📧 Contact

For questions or suggestions:  
**Your Name** – [your.email@example.com](mailto:your.email@example.com)
