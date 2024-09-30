# Financial News Aggregator

## Overview
The Financial News Aggregator is a Java-based application designed to efficiently fetch and cache news articles. By implementing a dual caching strategy using L1 (in-memory) and L2 (persistent) caches, this system optimizes data retrieval, reduces latency, and enhances user experience when accessing financial news data.

## Features
- **Dual Caching Strategy:** Implements both L1 and L2 caches to improve data retrieval times.
- **Database Integration:** Fetches news articles from a SQL database and stores them for efficient access.
- **Performance Measurement:** Measures and displays retrieval times for L1 cache, L2 cache, and the database, providing insights into performance improvements.
- **Easy to Extend:** The system is designed with scalability in mind, making it easy to add new features or modify existing ones.

## Technologies Used
- Java
- SQL (MySQL)
- Caching Libraries (Guava for L2 caching)
- JDBC (Java Database Connectivity)

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- A SQL database (e.g., MySQL) set up with the appropriate tables
- Maven (for dependency management)

### Installation
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/itsgits01/FinancialNewsAggregator.git
   cd FinancialNewsAggregator
2. Set Up the Database: Create a database and run the SQL scripts located in the /sql directory to set up the required tables.
3. Configure Database Connection: Update the database connection details in DatabaseConnection.java.
4. **Clone the Repository:**
   ```bash
   mvn clean install
5. Run the API Service: After building the project, run the APIService file to fetch data from the Alpha Vantage API and save it to the local database and cache.
6. Run the Test File: To test the retrieval times, execute the Test file located in the DatabaseSQL folder. Observe the retrieval times displayed in the console.


    **Example Output**
   ```bash
   News fetched from database:
   1. Title: News Article 1
   URL: http://example.com/news1
   Summary: Summary of news article 1
   Total news fetched from database: 10
   Time taken to fetch news from database: 500000 nanoseconds

   News fetched from L1 cache:
   1. Title: News Article 1
   URL: http://example.com/news1
   Summary: Summary of news article 1

   ...

   Total news fetched from L1 cache: 10
   Time taken to fetch news from L1 cache: 20000 nanoseconds

   ... 

   ----------------- Comparison of Retrieval Times -----------------
   Time taken to fetch from L1 cache: 20000 nanoseconds
   Time taken to fetch from L2 cache: 5000 nanoseconds
   Time taken to fetch from database: 500000 nanoseconds

  ## Contributing
   Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request. 


