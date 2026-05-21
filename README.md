Please redirect to "https://github.com/shivam-616/expense_tracker.git" for the expense tracker . This only has the sms extraction logic 

# SMS Extraction Service

A Spring Boot-based microservice that leverages **Spring AI** and **Google Gemini** to intelligently extract structured financial transaction details from raw bank SMS messages. 

The service includes a robust preprocessing pipeline to mask sensitive user data (like OTPs and account numbers) before querying the LLM, and operates in an event-driven architecture by publishing the structured extraction results to an **Apache Kafka** topic.

## 🚀 Features

* **Intelligent SMS Parsing:** Uses `gemini-2.5-flash` via Spring AI to extract complex fields such as merchant names, transaction amounts, categories, and references.
* **Data Privacy & Preprocessing:** A custom processing pipeline that validates, cleans, and masks sensitive data (OTPs, Phone Numbers, Account Numbers) before sending prompts to the AI model.
* **Normalization & Classification:** Automatically normalizes currency symbols (e.g., converting "Rs" or "₹" to "INR") and bank terminology (e.g., "debited" to "spent"), and classifies messages as `[EXPENSE]` or `[INCOME]`.
* **Event-Driven Architecture:** Publishes extracted `TransactionDetailsDTO` records to a Kafka topic for downstream microservices (e.g., an Expense Tracker).
* **Built-in UI:** Includes a static frontend (`index.html`) to visualize the AI extraction process.

## 🛠️ Tech Stack

* **Framework:** Java 17, Spring Boot 3.5.x
* **AI Integration:** Spring AI (`spring-ai-starter-model-google-genai`)
* **Messaging:** Apache Kafka (`spring-kafka`)
* **Database:** H2 In-Memory Database (for persistence and testing)
* **Build Tool:** Maven

## 🏗️ Architecture Workflow

1. A client sends a POST request with a raw SMS and a User ID header.
2. `SmsPreprocessingService` intercepts the SMS, ensuring it contains transactional keywords.
3. The pipeline cleans the text, masks sensitive info, and normalizes financial terms.
4. `AiService` injects the cleaned SMS into a prompt template (`sms-extraction.st`) and calls the Gemini API.
5. The LLM returns a structured JSON payload mapped to a Java Record (`TransactionDetailsDTO`).
6. `smsdataProducer` publishes this record to the Kafka topic `expense_service_topic`.

## ⚙️ Prerequisites

* Java 17+
* Maven 3.8+
* A running Kafka instance (default: `localhost:9092`)
* A valid Google Gemini API Key

## 💻 Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/shivam-616/expense_tracker.git](https://github.com/shivam-616/expense_tracker.git)
   cd sms_service
