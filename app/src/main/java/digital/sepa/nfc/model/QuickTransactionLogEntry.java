package digital.sepa.nfc.model;

import static digital.sepa.nfc.util.Utils.*;

/**
 * represents transaction logs for Quick (Austrian e-purse system)
 *
 * @author john
 */
public class QuickTransactionLogEntry extends AbstractTransactionLogEntry {

    private long amount2;
    private long remainingBalance;
    private long terminalInfos1;
    private long terminalInfos2;
    private Byte unknownByte1;
    private Byte unknownByte2;

    public long getAmount2() {
        return amount2;
    }

    public void setAmount2(long _amount2) {
        this.amount2 = _amount2;
    }

    public Byte getUnknownByte1() {
        return unknownByte1;
    }

    public void setUnknownByte1(Byte _unknownByte1) {
        this.unknownByte1 = _unknownByte1;
    }

    public Byte getUnknownByte2() {
        return unknownByte2;
    }

    public void setUnknownByte2(Byte _unknownByte2) {
        this.unknownByte2 = _unknownByte2;
    }

    public long getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(long _remainingBalance) {
        this.remainingBalance = _remainingBalance;
    }

    public long getTerminalInfos1() {
        return terminalInfos1;
    }

    public void setTerminalInfos1(long _terminalInfos1) {
        this.terminalInfos1 = _terminalInfos1;
    }

    public long getTerminalInfos2() {
        return terminalInfos2;
    }

    public void setTerminalInfos2(long _terminalInfos2s) {
        this.terminalInfos2 = _terminalInfos2s;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                "QuickTransactionLogEntry [\n  - transactionTimestamp: ");
        sb.append(formatDateWithTime(transactionTimestamp));
        sb.append("\n  - includes time: " + _hasTime);
        sb.append("\n  - amount: ");
        sb.append(formatBalance(_amount));
        sb.append("\n  - amount2: ");
        sb.append(formatBalance(amount2));
        sb.append("\n  - remaining balance: ");
        sb.append(formatBalance(remainingBalance));
        sb.append("\n  - atc: " + _atc);
        sb.append("\n  - currency: " + _currency);
        if (unknownByte1 != null) {
            sb.append("\n  - unknown byte 1: ");
            sb.append(byte2Hex(unknownByte1));
        }
        if (unknownByte2 != null) {
            sb.append("\n  - unknown byte 2: ");
            sb.append(byte2Hex(unknownByte2));
        }
        sb.append("\n  - terminal info 1: " + terminalInfos1);
        sb.append("\n  - terminal info 2: " + terminalInfos2);
        sb.append("\n");
        return sb.toString();
    }

}
