# Financial News Aggregator

## Overview
The Financial News Aggregator is a Java-based application designed to efficiently fetch and cache news articles. By implementing a dual caching strategy using L1 (in-memory) and L2 (persistent) caches, this system optimizes data retrieval, reduces latency, and enhances user experience when accessing news data.

## Features
- **L1 and L2 Caching:** Utilizes in-memory (L1) and persistent (L2) caching to speed up data retrieval.
- **News Retrieval:** Fetches news articles from the Alpha Vantage API and saves them to a local database.
- **Performance Measurement:** Compares retrieval times from L1 cache, L2 cache, and the database to evaluate the efficiency of the caching system.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/FinancialNewsAggregator.git
