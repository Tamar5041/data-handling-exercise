package data;

public class ResultEntry {

    private final int viewerID;
    private final long dataSubjectIds;

    public ResultEntry(int viewerID, long dataSubjectIds) {
        this.viewerID = viewerID;
        this.dataSubjectIds = dataSubjectIds;
    }

    public int getViewerID() {
        return viewerID;
    }

    public long getDataSubjectIds() {
        return dataSubjectIds;
    }
}