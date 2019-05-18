package digital.sepa.nfc.iso7816emv;

/**
 * Represents simple (not constructed) EMV tags and the corresponding value
 * bytes read from the card
 *
 * @author Johannes Zweng <johannes@zweng.at>
 */
public class TagAndValue {

    private final EmvTag tag;
    private final byte[] value;

    /**
     * Constructor
     *
     * @param tag
     * @param value
     */
    public TagAndValue(EmvTag tag, byte[] value) {
        super();
        this.tag = tag;
        this.value = value;
    }

    /**
     * @return the tag
     */
    public EmvTag getTag() {
        return tag;
    }

    /**
     * @return the value
     */
    public byte[] getValue() {
        return value;
    }

}
