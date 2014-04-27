package ws.tagit;

/**
 * Represents a parsed tag.
 *
 * @author Josh Long
 */
public class Tag {


    private String cleanTag, tag;

    public boolean isHashTag() {
        return tag.startsWith("#");
    }

    public boolean isMultiTokenTag() {
        return tag.contains(" ");
    }

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "cleanTag='" + cleanTag + '\'' +
                ", tag='" + tag + '\'' +

                '}';
    }

    public Tag(String cleanTag, String tag) {
        this.cleanTag = cleanTag;
        this.tag = tag;
    }

    public String getCleanTag() {
        return cleanTag;
    }

    public String getTag() {
        return tag;
    }

}
