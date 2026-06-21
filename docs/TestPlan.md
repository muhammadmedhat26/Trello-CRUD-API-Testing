# Test Plan — Trello CRUD API Testing

**Project:** Trello-CRUD-API-Testing
**Repository:** github.com/muhammadmedhat26/Trello-CRUD-API-Testing
**Version:** 1.0

---

## 1. Project Introduction

### 1.1 Testing Website/Target

Trello REST API — `https://api.trello.com` (live production API, version 1).

### 1.2 Summary

This project automates testing of Trello's REST API across its core resource hierarchy: **Board → List → Card → Checklist**. It started as a manual Postman test design exercise and was converted into a Java automation suite using RestAssured, TestNG, and Allure. The Postman collection is kept in the repo as the original design reference.

### 1.3 Purpose

To verify that the Trello API behaves correctly and consistently across the full CRUD lifecycle of its core resources, and to document and assert against the API's actual authentication and validation error behavior — which in several cases differs from what the official documentation implies.

---

## 2. Scope

### 2.1 In Scope (Modules)

- **CRUD lifecycle** — Board, List, Card, Checklist: Create → Get → Update → Get → Delete/Archive → Get
- **Authentication validation** — missing/invalid key and token, across all 4 resources and applicable methods
- **Parameter & path validation** — missing required fields, empty fields, missing path IDs, nonexistent resource IDs
- **Boundary cases** — board name length, whitespace-only name
- Manual exploratory testing via the included Postman collection

### 2.2 Out of Scope

- UI testing of Trello's web/mobile clients
- Performance, load, or stress testing
- Security testing beyond basic auth-failure validation (no fuzzing/injection)
- Trello resources outside Board/List/Card/Checklist (Members, Organizations, Webhooks, Labels, etc.)
- Cascading-failure testing (behavior on child resources after parent deletion) — deferred, handled manually outside this suite
- CI/CD pipeline integration (not currently configured)

---

## 3. Test Objectives

- Confirm each resource can be created, retrieved, updated, and removed (deleted or archived) successfully, with response data matching what was submitted.
- Confirm the API rejects requests with missing or invalid authentication, returning the correct status code and exact error message for each case.
- Confirm the API rejects requests with missing/invalid required parameters and missing/nonexistent resource IDs, with the correct status code and error message.
- Surface and document any API behavior that differs from the official documentation, so future test maintenance is based on verified behavior rather than assumption.

---

## 4. Test Environment

| Component | Detail |
|---|---|
| Target | `https://api.trello.com` (live, no sandbox) |
| Language | Java |
| Build tool | Maven |
| Test framework | TestNG |
| HTTP/assertions | RestAssured |
| Reporting | Allure |
| IDE | IntelliJ IDEA |
| Manual tool | Postman (`/postman` collection + environment) |
| Credentials | `TRELLO_API_KEY`, `TRELLO_TOKEN` — OS environment variables, read via `System.getenv()` in `BaseTest` |
| Execution config | `src/test/resources/testng.xml` — fixed class order, no parallelism |

---

## 5. Entry & Exit Criteria

### 5.1 Entry Criteria

- Valid Trello key and token set as `TRELLO_API_KEY` / `TRELLO_TOKEN`
- `mvn clean install` completes successfully
- `CreateGetTests2` has run at least once to populate live resource IDs (or `trello_runtime.properties` already holds valid, non-deleted IDs)

### 5.2 Exit Criteria

- All classes in `testng.xml` execute without unexpected build failure
- Allure report generated and reviewed against the known API behaviors in §6.1
- Any new test case added to the code is reflected in `docs/TEST_CASES.md`

---

## 6. Test Approach

Tests run in a fixed sequence to satisfy data dependencies, defined in `testng.xml`:

1. **`CreateGetTests2`** — creates Board → List → Card → Checklist, verifies each via GET. Populates the IDs everything else depends on.
2. **`AuthenticationTests`** — auth failure cases across all resources/methods.
3. **`ParameterTests`** — required-field, path-param, and boundary cases.
4. **`UpdateGetTests`** — renames each resource, verifies persistence.
5. **`DeleteGetTests`** — deletes Checklist/Card, archives/unarchives List, deletes Board, verifies each.

Steps 2–3 run before update/delete intentionally, so the resources they reference still exist. Resource IDs are tracked via `BaseTest`'s smart getters: in-memory first, falling back to `trello_runtime.properties` (written by `ConfigUtils`) if a class runs in isolation.

### 6.1 Testing Types & Technique

| Type | Technique |
|---|---|
| Functional / Positive | Happy-path CRUD per resource, asserting response fields match submitted data |
| Negative — Authentication | Boundary/equivalence partitioning on key/token presence and validity |
| Negative — Parameter | Equivalence partitioning on missing/empty/invalid required fields and IDs |
| Boundary | Long-string and whitespace-only input on board name |
| Exploratory | Manual Postman testing prior to/alongside automation |

**Known API behaviors asserted against** (verified live, not assumed from docs):
- GET with both credentials missing → `401 "unauthorized permission requested"`; every other method → `401 "missing scopes"`
- Lists are archived, never hard-deleted (`PUT .../closed?value=true` → `200`, never `404`)
- Nonexistent `idBoard`/`idCard` on creation → `401`, not `400`
- Nonexistent `listId` → `"model not found"`; same case for board/card/checklist → `"The requested resource was not found."`
- Missing path ID entirely → routing-level `404` (`"Cannot PUT ..."`), distinct from the not-found message above

---

## 7. Deliverables, Tools, and Roles

### Deliverables
- Automated test suite (`src/test/java`)
- Test case inventory (`docs/TEST_CASES.md`)
- Postman collection + environment (`/postman`)
- Allure HTML report (generated per run)
- This test plan

### Tools
- Maven, TestNG, RestAssured, Allure, IntelliJ IDEA, Postman

### Roles
- Single contributor: test design, automation, execution, and maintenance (Muhammad Medhat)

---

## Open Items

- `ParameterTests.java` has 2 test methods (`createBoard_boundaryLongName`, `createBoard_whitespaceOnlyName`) not yet reflected in `TEST_CASES.md`
- `pom.xml` includes an unused Selenium dependency — confirm if planned or remove
- Both `AuthenticationTests.java` and `ParameterTests.java` carry a large commented-out `@BeforeClass` block, safe to delete
