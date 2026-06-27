# Trello API Test Automation

Automated REST API test suite for Trello, built with **RestAssured**, **TestNG**, and **Allure**. The project validates a full CRUD user journey across Trello's Board → List → Card → Checklist hierarchy, along with authentication failures and parameter/path validation.

This started as a manual Postman test case design exercise and was converted into a maintainable Java automation suite. The original Postman collection and environment are kept in the repo as the design reference the Java suite was built from.

## What This Project Tests

The suite is organized into independent test classes covering three areas:

| Area | Class | What it verifies |
|---|---|---|
| **CRUD Journey** | `CreateGetTests2`, `UpdateGetTests`, `DeleteGetTests` | End-to-end lifecycle: create → verify → update → verify → archive/delete → verify, across Board, List, Card, and Checklist |
| **Authentication** | `AuthenticationTests` | Missing/invalid `key` and `token` combinations on every endpoint, and the exact error message Trello returns for each |
| **Parameter Validation** | `ParameterTests` | Missing required fields, missing path parameters, and nonexistent resource IDs |

A full breakdown of every test case — ID, title, request data, and expected/actual result — lives in [`docs/TEST_CASES.md`](docs/TEST_CASES.md).

## Tech Stack

- **Java 11+**
- **IntelliJ IDEA** — primary development environment
- **Maven** — build and dependency management
- **RestAssured** — HTTP request execution and response assertions
- **TestNG** — test runner, execution ordering via `dependsOnMethods`
- **Allure** — HTML test reporting
- **Postman** — original manual test design and exploration tool (collection included in repo)

## Prerequisites

- **IntelliJ IDEA** (Community or Ultimate)
- **Java JDK 11 or higher**
- **Maven**
- **Postman**
- A Trello account
- A Trello API **key** and **token**

### Generating a Trello API Key and Token

1. Log into Trello, then go to **https://trello.com/power-ups/admin** (or `https://trello.com/app-key` on older accounts).
2. Copy the **API Key** shown on that page.
3. Click the **Token** link on the same page (or visit the manual token generation URL Trello provides), and authorize it. This generates a **Token** scoped to your account.
4. Keep both values handy — you'll need them in both Postman and IntelliJ in the steps below.

> Tokens can expire or be revoked. If you start getting unexpected `401` errors across previously-passing tests, regenerate your token first.

## Setting Up Postman

The `postman/` folder contains the original collection and environment this project was designed from.

1. Open Postman → **Import** → select `postman/Trello APIs.postman_collection.json`.
2. Import the environment too: **Import** → select `postman/TrelloEnv.postman_environment.json`.
3. Select **TrelloEnv** as the active environment (top-right dropdown).
4. Open the environment (click the eye icon, then **Edit**) and fill in the values for:
   - `baseURL` → `https://api.trello.com`
   - `key` → your Trello API key
   - `token` → your Trello API token
5. Save. You can now run any request in the collection manually to explore behavior before — or instead of — running the Java suite.

## Project Structure

```
src/test/java/
├── BaseTest.java              # KEY/TOKEN read from env vars, BASE_URL constant, smart getters/setters for resource IDs
├── ConfigUtils.java           # Persists generated IDs to a properties file between runs
├── CreateGetTests2.java       # Tier 1 — Create + Get verification (Board → List → Card → Checklist)
├── UpdateGetTests.java        # Tier 1 — Update + Get verification
├── DeleteGetTests.java        # Tier 1 — Delete/Archive + Get verification
├── AuthenticationTests.java   # Tier 2 — Auth failure cases
└── ParameterTests.java        # Tier 3 — Parameter/path validation cases

src/test/resources/
├── testng.xml                 # Suite definition and class execution order
└── trello_runtime.properties  # Auto-generated — stores the last run's resource IDs

docs/
├── TestPlan.md                 # Full Test Plan
└── TEST_CASES.md              # Full test case inventory (ID, steps, expected/actual results)

postman/
├── Trello APIs.postman_collection.json
└── TrelloEnv.postman_environment.json
```

## Setting Up IntelliJ

1. Open IntelliJ IDEA → **File → Open** → select the project's `pom.xml` (or the project root) to import it as a Maven project.
2. Let Maven finish resolving dependencies (check the bottom status bar).
3. `BaseTest.java` reads the key and token from **OS environment variables** rather than hardcoded values:
   ```java
   protected static final String KEY   = System.getenv("TRELLO_KEY");
   protected static final String TOKEN = System.getenv("TRELLO_TOKEN");
   ```
   This means the variables must exist in your environment *before* IntelliJ starts (IntelliJ inherits env vars from the process that launched it).

### Setting the environment variables

**Option A — Windows, via `setx` (persists across reboots):**
```
setx TRELLO_KEY "your_key_here"
setx TRELLO_TOKEN "your_token_here"
```
Close and reopen IntelliJ completely after running this (a relaunch from the Start Menu is not enough if IntelliJ was already running — fully quit it first).

**Option B — macOS/Linux, via shell profile:**
Add to `~/.zshrc`, `~/.bashrc`, or equivalent:
```
export TRELLO_KEY="your_key_here"
export TRELLO_TOKEN="your_token_here"
```
Then restart your terminal and launch IntelliJ from it (`idea .`), or log out/in so the variables propagate to the GUI launcher.

**Option C — Set directly in IntelliJ's Run Configuration (no OS-level changes):**
1. Run → Edit Configurations
2. Select your TestNG configuration (or create one targeting `testng.xml`)
3. Under **Environment variables**, add:
   ```
   TRELLO_KEY=your_key_here;TRELLO_TOKEN=your_token_here
   ```
4. Apply and run from this configuration going forward.

Option C is the most reliable if Options A/B don't propagate correctly to IntelliJ's process on your machine — it scopes the variables to the run configuration itself rather than relying on the OS environment.

> Verify the variables are visible to Java by running a quick `System.out.println(System.getenv("TRELLO_KEY"))` test before running the full suite, if you're unsure whether they've propagated.

5. From the project root, install dependencies via terminal (or IntelliJ's Maven tool window → Lifecycle → install):
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

## Trello API Documentation

For the full official API reference (endpoints, parameters, response schemas):

**https://developer.atlassian.com/cloud/trello/rest/**
