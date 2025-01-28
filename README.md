# Portfolio Management API

This project is a **RESTful API** for managing user portfolios. It allows users to organize and manage their **projects**, **skills**, **experience**, and **education** securely.

## Purpose

The API is designed for users who want to showcase their portfolios online. Each user can:
- Create and manage portfolios.
- Add and update information such as work experience, education, and more.
- Securely access and modify their data using **JWT authentication**.

---

## Services and Methods

### Portfolio Service
- **`savePortfolio(PortfolioRequestDto portfolioRequestDto)`**  
  Adds a new portfolio. Ensures that the portfolio title is unique.

- **`getAllPortfolios()`**  
  Retrieves all portfolios in the database.

- **`updatePortfolio(Long portfolioId, PortfolioRequestDto portfolioRequestDto)`**  
  Updates an existing portfolio. Ensures only the owner can update their portfolio.

- **`deletePortfolio(Long portfolioId)`**  
  Deletes a portfolio. Ensures only the owner can delete their portfolio.

- **`getPortfoliosByUserId(Long userId)`**  
  Retrieves all portfolios associated with a specific user.

---

### Experience Service
- **`addExperience(ExperienceRequestDto experienceRequestDto)`**  
  Adds a new experience to a specific portfolio.

- **`getAllExperiences()`**  
  Retrieves all experiences.

- **`updateExperience(Long experienceId, ExperienceRequestDto experienceRequestDto)`**  
  Updates an existing experience. Ensures only the owner can update their experience.

- **`deleteExperience(Long experienceId)`**  
  Deletes an experience. Ensures only the owner can delete their experience.

---

### Education Service
- **`addEducation(EducationRequestDto educationRequestDto)`**  
  Adds new education details to a specific portfolio.

- **`getAllEducations()`**  
  Retrieves all education records.

- **`updateEducation(Long educationId, EducationRequestDto educationRequestDto)`**  
  Updates an existing education record. Ensures only the owner can update their education.

- **`deleteEducation(Long educationId)`**  
  Deletes an education record. Ensures only the owner can delete their education.

---

## Security
- **JWT Authentication** is implemented to ensure secure access.  
- Users can only modify their own portfolios and associated data.

---

## Tools and Technologies
- **Java 21**
- **Spring Boot** (Spring Data JPA, Spring Security)
- **ModelMapper**
- **PostgreSQL** (for development and testing)
- **Swagger** (API documentation)

---

## How to Run

1. Installation and Setup:
   ```bash
   git clone https://github.com/KenanZalov/InternIntelligence_PortfolioApiDevelopment
   cd InternIntelligence_PortfolioApiDevelopment
