# Company API

### 1. Get Company List (ID and Name Only)
- **Endpoint:** `GET /companies`
- **Response:**
    ```json
    [
        { "id": 1, "name": "Company A" },
        { "id": 2, "name": "Company B" },
        { "id": 3, "name": "Company C" }
    ]
    ```

---

### 2. Get a Specific Company (ID and Name Only)
- **Endpoint:** `GET /companies/{id}`
- **Response:**
    ```json
    { "id": 1, "name": "Company A" }
    ```

---

### 3. Get All Employees Under a Specific Company
- **Endpoint:** `GET /companies/{id}/employees`
- **Response:**
    ```json
    [
        { "id": 1, "name": "Tom", "age": 25, "gender": "MALE", "salary": 8000.0 },
        { "id": 2, "name": "Jerry", "age": 28, "gender": "FEMALE", "salary": 9000.0 }
    ]
    ```

---

### 4. Get Companies with Pagination
- **Endpoint:** `GET /companies?page={page}&size={size}`
- **Response:**
    ```json
    [
        { "id": 1, "name": "Company A" },
        { "id": 2, "name": "Company B" },
        { "id": 3, "name": "Company C" },
        { "id": 4, "name": "Company D" },
        { "id": 5, "name": "Company E" }
    ]
    ```

---

### 5. Update an Employee
- **Endpoint:** `PUT /employees/{id}`
- **Request Body:**
    ```json
    {
        "name": "Tom",
        "age": 30,
        "gender": "MALE",
        "salary": 8500.0,
        "companyId": 1
    }
    ```
- **Response:**
    ```json
    {
        "id": 1,
        "name": "Tom",
        "age": 30,
        "gender": "MALE",
        "salary": 8500.0,
        "companyId": 1
    }
    ```

---

### 6. Add a New Company
- **Endpoint:** `POST /companies`
- **Request Body:**
    ```json
    {
        "name": "New Company"
    }
    ```
- **Response:**
    ```json
    {
        "id": 10,
        "name": "New Company"
    }
    ```

---

### 7. Delete a Company
- **Endpoint:** `DELETE /companies/{id}`
- **Response Status:** `204 No Content`
