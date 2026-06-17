public class BaseTest {

    protected static final String BASE_URL = "https://api.trello.com";
    protected static final String KEY = System.getenv("TRELLO_API_KEY");
    protected static final String TOKEN = System.getenv("TRELLO_TOKEN");

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

    protected static void setBoardId(String id) {
        boardId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }

    protected static String getListId() {
        if (listId == null || listId.isEmpty()) {
            listId = ConfigUtils.getID("listId");
        }
        return listId;
    }

    protected static void setListId(String id) {
        listId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }

    // ─────────────────────────────────────────────────────────────────
    // SETTERS (Save to Memory AND write immediately to File)
    // ─────────────────────────────────────────────────────────────────

    protected static String getCardId() {
        if (cardId == null || cardId.isEmpty()) {
            cardId = ConfigUtils.getID("cardId");
        }
        return cardId;
    }

    protected static void setCardId(String id) {
        cardId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }

    protected static String getChecklistId() {
        if (checklistId == null || checklistId.isEmpty()) {
            checklistId = ConfigUtils.getID("checklistId");
        }
        return checklistId;
    }

    protected static void setChecklistId(String id) {
        checklistId = id;
        ConfigUtils.saveIDs(boardId, listId, cardId, checklistId);
    }
}
