
public class BaseTest {

    protected static final String BASE_URL = "https://api.trello.com";
    protected static final String KEY      = "c6346350fdbbd7588d436e8b1bc22fe5";
    protected static final String TOKEN    = "ATTAf7d99ab06ebe7e5e36df5fb0f84927976f740e3152c85b78178932641a5ffab8C8EB9638";

    protected static String boardId;
    protected static String listId;
    protected static String cardId;
    protected static String checklistId;

    // ─────────────────────────────────────────────────────────────────
    // SMART GETTERS (Memory First -> File Fallback)
    // ─────────────────────────────────────────────────────────────────

    protected static String getBoardId() {
        if (boardId == null || boardId.isEmpty()) {
            boardId = ConfigUtils.getID("boardId");
        }
        return boardId;
    }

    protected static String getListId() {
        if (listId == null || listId.isEmpty()) {
            listId = ConfigUtils.getID("listId");
        }
        return listId;
    }

    protected static String getCardId() {
        if (cardId == null || cardId.isEmpty()) {
            cardId = ConfigUtils.getID("cardId");
        }
        return cardId;
    }

    protected static String getChecklistId() {
        if (checklistId == null || checklistId.isEmpty()) {
            checklistId = ConfigUtils.getID("checklistId");
        }
        return checklistId;
    }

    // ─────────────────────────────────────────────────────────────────
    // SETTERS (Save to Memory AND write immediately to File)
    // ─────────────────────────────────────────────────────────────────

    protected static void setBoardId(String id) {
        boardId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }

    protected static void setListId(String id) {
        listId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }

    protected static void setCardId(String id) {
        cardId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }

    protected static void setChecklistId(String id) {
        checklistId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }
}
