# Test Case Inventory

This document lists every automated test case in the suite, grouped by tier. All cases below have been executed against the live Trello API and are currently passing (see `Status` column in the original spreadsheet report for full Actual Result detail).

For the source-of-truth spreadsheet (with full Expected/Actual Result text), see the project's test report Excel file. This markdown version is a condensed reference for quick browsing on GitHub.

---

## Tier 1 — End-to-End CRUD (24 cases)

Full lifecycle across Board → List → Card → Checklist: create, verify, update, verify, archive/delete, verify.

| ID | Title | Method | Endpoint |
|---|---|---|---|
| TC-T1-001 | Create board with valid name and credentials | POST | `/1/boards/` |
| TC-T1-002 | Retrieve newly created board, verify fields match | GET | `/1/boards/{id}` |
| TC-T1-003 | Create list inside existing board | POST | `/1/lists` |
| TC-T1-004 | Retrieve newly created list, verify fields match | GET | `/1/lists/{id}` |
| TC-T1-005 | Create card inside existing list | POST | `/1/cards` |
| TC-T1-006 | Retrieve newly created card, verify fields match | GET | `/1/cards/{id}` |
| TC-T1-007 | Create checklist on existing card | POST | `/1/checklists` |
| TC-T1-008 | Retrieve newly created checklist, verify fields match | GET | `/1/checklists/{id}` |
| TC-T1-009 | Rename existing board | PUT | `/1/boards/{id}` |
| TC-T1-010 | Verify board rename persisted | GET | `/1/boards/{id}` |
| TC-T1-011 | Rename existing list | PUT | `/1/lists/{id}` |
| TC-T1-012 | Verify list rename persisted | GET | `/1/lists/{id}` |
| TC-T1-013 | Rename existing card | PUT | `/1/cards/{id}` |
| TC-T1-014 | Verify card rename persisted | GET | `/1/cards/{id}` |
| TC-T1-015 | Rename existing checklist | PUT | `/1/checklists/{id}` |
| TC-T1-016 | Verify checklist rename persisted | GET | `/1/checklists/{id}` |
| TC-T1-017 | Delete existing checklist | DELETE | `/1/checklists/{id}` |
| TC-T1-018 | Verify deleted checklist returns 404 | GET | `/1/checklists/{id}` |
| TC-T1-019 | Delete existing card | DELETE | `/1/cards/{id}` |
| TC-T1-020 | Verify deleted card returns 404 | GET | `/1/cards/{id}` |
| TC-T1-021 | Archive list (`closed=true`) | PUT | `/1/lists/{id}/closed` |
| TC-T1-022 | Verify archived list returns 200 with `closed=true` (not 404) | GET | `/1/lists/{id}` |
| TC-T1-023 | Delete existing board | DELETE | `/1/boards/{id}` |
| TC-T1-024 | Verify deleted board returns 404 | GET | `/1/boards/{id}` |

> Note: an earlier version of this suite also included an unarchive step (`value=false`) and its verification — kept optional since it's not part of the core lifecycle.

---

## Tier 2A — Authentication Failures (35 cases)

Covers missing/invalid `key` and `token` on every method, across all four resources, plus the two GET-specific edge cases.

**Pattern per resource (Board, List, Card, Checklist) × method (POST/Create, GET, PUT, DELETE):**

| Scenario | Expected Status | Expected Body |
|---|---|---|
| Missing `key` | 401 | `"invalid key"` |
| Missing `token` | 401 | `"missing scopes"` |

This pattern repeats for all 4 resources across their applicable methods — **TC-Auth-001 through TC-Auth-032**.

**Additional board-specific edge cases:**

| ID | Title | Expected Status | Expected Body |
|---|---|---|---|
| TC-Auth-033 | Invalid key value on board creation | 401 | `"invalid key"` |
| TC-Auth-034 | Invalid token value on board creation | 401 | `"invalid app token"` |
| TC-Auth-035 | GET with both key and token missing | 401 | `"unauthorized permission requested"` |

> Important: a GET with both credentials missing returns a *different* message (`"unauthorized permission requested"`) than every other method missing both credentials (`"missing scopes"`). This is the one true asymmetry in Trello's auth layer.

---

## Tier 2B — Parameter & Path Validation (36 cases)

Covers missing required fields, missing path parameters, and nonexistent resource IDs.

### Board

| ID | Title | Expected Status | Expected Body |
|---|---|---|---|
| TC-T2B-001 | Missing `name` on create | 400 | `"invalid value for name"` |
| TC-T2B-002 | Empty string `name` on create | 400 | `"invalid value for name"` |
| TC-T2B-003 | Missing path ID on GET | 404 | `"Cannot GET /1/boards/?..."` |
| TC-T2B-004 | Nonexistent ID on GET | 404 | `"The requested resource was not found."` |
| TC-T2B-005 | Missing path ID on PUT | 404 | `"Cannot PUT /1/boards/?..."` |
| TC-T2B-006 | Nonexistent ID on PUT | 404 | `"The requested resource was not found."` |
| TC-T2B-007 | Missing path ID on DELETE | 404 | `"Cannot DELETE /1/boards/?..."` |
| TC-T2B-008 | Nonexistent ID on DELETE | 404 | `"The requested resource was not found."` |

### List

| ID | Title | Expected Status | Expected Body |
|---|---|---|---|
| TC-T2B-009 | Missing `name` on create | 400 | `"invalid value for name"` |
| TC-T2B-010 | Empty string `name` on create | 400 | `"invalid value for name"` |
| TC-T2B-011 | Missing `idBoard` on create | 400 | `"invalid value for idBoard"` |
| TC-T2B-012 | Nonexistent `idBoard` on create | 401 | `"unauthorized board list requested {id}"` |
| TC-T2B-013 | Missing path ID on GET | 404 | `"Cannot GET /1/lists/?..."` |
| TC-T2B-014 | Nonexistent ID on GET | 404 | `"model not found"` |
| TC-T2B-015 | Missing path ID on PUT | 404 | `"Cannot PUT /1/lists/?..."` |
| TC-T2B-016 | Nonexistent ID on PUT | 404 | `"model not found"` |
| TC-T2B-017 | Nonexistent ID on Archive | 404 | `"model not found"` |
| TC-T2B-018 | Missing path ID on Archive | 400 | `"invalid id"` |
| TC-T2B-019 | Invalid `value` (not true/false) on Archive | 400 | `"invalid value for value"` |
| TC-T2B-020 | Missing `value` param on Archive | 400 | `"invalid value for value"` |

### Card

| ID | Title | Expected Status | Expected Body |
|---|---|---|---|
| TC-T2B-021 | Missing `idList` on create | 400 | `"invalid value for idList"` |
| TC-T2B-022 | Nonexistent `idList` on create | 404 | `"could not find the board that the card belongs to"` |
| TC-T2B-023 | Missing path ID on GET | 404 | `"Cannot GET /1/cards/?..."` |
| TC-T2B-024 | Nonexistent ID on GET | 404 | `"The requested resource was not found."` |
| TC-T2B-025 | Missing path ID on PUT | 404 | `"Cannot PUT /1/cards/?..."` |
| TC-T2B-026 | Nonexistent ID on PUT | 404 | `"The requested resource was not found."` |
| TC-T2B-027 | Missing path ID on DELETE | 404 | `"Cannot DELETE /1/cards/?..."` |
| TC-T2B-028 | Nonexistent ID on DELETE | 404 | `"The requested resource was not found."` |

### Checklist

| ID | Title | Expected Status | Expected Body |
|---|---|---|---|
| TC-T2B-029 | Missing `idCard` on create | 400 | `"invalid value for idCard"` |
| TC-T2B-030 | Nonexistent `idCard` on create | 401 | `"unauthorized board requested"` |
| TC-T2B-031 | Missing path ID on GET | 404 | `"Cannot GET /1/checklists/?..."` |
| TC-T2B-032 | Nonexistent ID on GET | 404 | `"The requested resource was not found."` |
| TC-T2B-033 | Missing path ID on PUT | 404 | `"Cannot PUT /1/checklists/?..."` |
| TC-T2B-034 | Nonexistent ID on PUT | 404 | `"The requested resource was not found."` |
| TC-T2B-035 | Missing path ID on DELETE | 404 | `"Cannot DELETE /1/checklists/?..."` |
| TC-T2B-036 | Nonexistent ID on DELETE | 404 | `"The requested resource was not found."` |

---

## Tier 2C — Cascading Failures *(in progress)*

Covers behavior when operating on child resources after a parent resource (board, list, or card) has been deleted. Not yet automated — being handled manually. See the main spreadsheet report for planned scenarios once finalized.

---

## Summary

| Tier | Cases | Status |
|---|---|---|
| Tier 1 — E2E CRUD | 24 | ✅ Automated, passing |
| Tier 2A — Auth | 35 | ✅ Automated, passing |
| Tier 2B — Parameters | 36 | ✅ Automated, passing |
| Tier 2C — Cascading | TBD | 🚧 Manual / in progress |
| **Total automated** | **95** | |
