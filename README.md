# Trello API Test Automation

Automated REST API test suite for Trello, built with **RestAssured**, **TestNG**, and **Allure**. The project validates a full CRUD user journey across Trello's Board → List → Card → Checklist hierarchy, along with authentication failures, parameter validation, and resource-not-found behavior.

This started as a manual Postman test case design exercise and was converted into a maintainable Java automation suite, with the original Postman collection kept as a reference and exploration tool.

## What This Project Tests

The suite is organized into independent test classes covering four areas:

| Area | Class | What it verifies |
|---|---|---|
| **CRUD Journey** | `CreateGetTests2`, `UpdateGetTests`, `DeleteGetTests` | End-to-end lifecycle: create → verify → update → verify → archive/delete → verify, across Board, List, Card, and Checklist |
| **Authentication** | `AuthenticationTests` | Missing/invalid `key` and `token` combinations on every endpoint, and the exact error message Trello returns for each |
| **Parameter Validation** | `ParameterTests` | Missing required fields, missing path parameters, and nonexistent resource IDs |
| **Cascading Failures** | *(manual, in progress)* | Behavior when operating on child resources after a parent has been deleted |

A full breakdown of every test case — ID, title, request data, and expected/actual result — lives in [`docs/TEST_CASES.md`](docs/TEST_CASES.md).

## Tech Stack

- **Java 11+**
- **Maven** — build and dependency management
- **RestAssured** — HTTP request execution and response assertions
- **TestNG** — test runner, execution ordering via `dependsOnMethods`
- **Allure** — HTML test reporting

## Prerequisites

- Java JDK 11 or higher
- Maven
- A Trello account
- A Trello API **key** and **token**

### Generating a Trello API Key and Token

1. Log into Trello, then go to **https://trello.com/power-ups/admin** (or `https://trello.com/app-key` on older accounts).
2. Copy the **API Key** shown on that page.
3. Click the **Token** link on the same page (or visit the manual token generation URL Trello provides), and authorize it. This generates a **Token** scoped to your account.
4. Keep both values handy — you'll paste them into `BaseTest.java` in the next step.

> Tokens can expire or be revoked. If you start getting unexpected `401` errors across previously-passing tests, regenerate your token first.

## Project Structure

```
src/test/java/
├── BaseTest.java              # Shared constants (KEY, TOKEN, BASE_URL) + smart getters/setters for resource IDs
├── ConfigUtils.java           # Persists generated IDs to a properties file between runs
├── CreateGetTests2.java       # Tier 1 — Create + Get verification (Board → List → Card → Checklist)
├── UpdateGetTests.java        # Tier 1 — Update + Get verification
├── DeleteGetTests.java        # Tier 1 — Delete/Archive + Get verification
├── AuthenticationTests.java   # Tier 2A — Auth failure cases
└── ParameterTests.java        # Tier 2B — Parameter/path validation cases

src/test/resources/
├── testng.xml                 # Suite definition and class execution order
└── trello_runtime.properties  # Auto-generated — stores the last run's resource IDs

docs/
└── TEST_CASES.md              # Full test case inventory (ID, steps, expected/actual results)

postman/
├── Trello APIs.postman_collection.json
└── TrelloEnv.postman_environment.json
```

## Setup

1. Clone the repository.
2. Open `BaseTest.java` and replace the placeholder values:
   ```java
   protected static final String KEY   = "your_key_here";
   protected static final String TOKEN = "your_token_here";
   ```
3. From the project root, install dependencies:
   ```
   mvn clean install
   ```

## Running the Tests

### Run everything (recommended order)

The full suite runs Create → Update → Delete → Auth → Parameter tests in the order defined in `testng.xml`:

```
mvn test
```

This uses `src/test/resources/testng.xml`, which lists the classes in the correct dependency order:

```xml
<class name="CreateGetTests2"/>
<class name="AuthenticationTests"/>
<class name="ParameterTests"/>
<class name="UpdateGetTests"/>
<class name="DeleteGetTests"/>
```

### Viewing the Allure report

After a run:

```
mvn allure:report
mvn allure:serve
```

`allure:serve` opens the report directly in your browser.

## Running Tests Separately

Each test class can technically run on its own, but `AuthenticationTests` and `ParameterTests` depend on a Board/List/Card/Checklist already existing — they don't create their own data. This is handled through **`BaseTest`'s smart getters**, not the optional `@BeforeClass` isolation block:

- `getBoardId()`, `getListId()`, `getCardId()`, `getChecklistId()` first check in-memory static fields.
- If those are empty (e.g. you're running `AuthenticationTests` in a fresh JVM without running `CreateGetTests2` first), the getters fall back to reading the last known IDs from `trello_runtime.properties` — written automatically by `CreateGetTests2` via the matching setters (`setBoardId()`, etc.).

**Practical workflow:**

1. Run `CreateGetTests2` once to generate fresh, live resources. IDs are saved to the properties file automatically.
2. Run `AuthenticationTests` or `ParameterTests` on their own, as many times as you like — they'll reuse the IDs from the properties file.
3. Run `DeleteGetTests` last, once you're done, to clean up the created board (and therefore its list and card).

> Running `AuthenticationTests` or `ParameterTests` *after* `DeleteGetTests` has already run will fail, since the stored IDs will point to resources that no longer exist. Re-run `CreateGetTests2` to refresh them.

To run a single class directly from your IDE, just right-click and run — no special configuration needed beyond having valid IDs available as described above.

## Known Trello API Behaviors

A few real responses from Trello diverge from what the official documentation implies. These are captured precisely in the test assertions and worth knowing if you're extending the suite:

- Lists cannot be hard-deleted via the API — `DELETE` is not supported on lists. The only removal mechanism is archiving (`PUT /1/lists/{id}/closed?value=true`), which returns `200` with `closed: true`, not `404`.
- A nonexistent `idBoard` on list creation returns `401 "unauthorized board list requested {id}"`, not the `400` you'd expect for a malformed parameter.
- A nonexistent `idCard` on checklist creation returns `401 "unauthorized board requested"`.
- A nonexistent `idList` on card creation returns `404 "could not find the board that the card belongs to"`.
- A nonexistent `listId` on `GET`/`PUT`/Archive returns `404 "model not found"`, while the equivalent case for boards, cards, and checklists returns `404 "The requested resource was not found."`
- Omitting a required path ID entirely (e.g. `PUT /1/boards/`) returns a routing-level `404` with a raw `"Cannot PUT /1/boards/?..."` message, distinct from the resource-not-found message above.
- A GET request missing **both** `key` and `token` returns `401 "unauthorized permission requested"`, while every other method missing both returns `401 "missing scopes"` instead.

## License

This project is for personal learning and portfolio purposes.
