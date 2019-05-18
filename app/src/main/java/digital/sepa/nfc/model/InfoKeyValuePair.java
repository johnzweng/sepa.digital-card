package digital.sepa.nfc.model;

public class InfoKeyValuePair {
    private final String name;
    private final String tagName;
    private final String value;
    private final boolean isSectionHeader;

    /**
     * @param name
     * @param value
     */
    public InfoKeyValuePair(String name, String tagName, String value) {
        super();
        this.name = name;
        this.tagName = tagName;
        this.value = value;
        this.isSectionHeader = false;
    }

    public InfoKeyValuePair(String sectionHeaderName) {
        super();
        this.name = sectionHeaderName;
        this.value = null;
        this.tagName = null;
        this.isSectionHeader = true;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return isSectionHeader
     */
    public boolean isSectionHeader() {
        return isSectionHeader;
    }

}
