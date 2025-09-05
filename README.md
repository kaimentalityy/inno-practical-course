# Robot War Simulation

This is a simple Java project simulating two factions competing to build the strongest robot army in 100 days.

## Project Structure
- **Factory**: Produces up to 10 random parts per day.
- **Factions**: World and Wednesday, each collecting up to 5 parts per day.
- **Parts**: Head, Torso, Hand, Feet.
- **Robots**: Require one of each part to be complete.

## How to Run
1. Build the project:
   ```bash
   mvn clean package
   ```
2. Run the simulation:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.robotwar.RobotWarSimulation"
   ```
3. Run tests:
   ```bash
   mvn test
   ```

## Requirements
- Java 17+
- Maven 3.6+
