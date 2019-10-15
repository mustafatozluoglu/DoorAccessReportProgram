
/**
 * created by mustafa.tozluoglu on 4.10.2019
 */
public class Record {

    private String genTime;
    private String seqId;
    private String type;
    private String status;
    private String p;
    private String readerPointData;
    private String site;
    private String cardNumber;
    private String account;
    private String name;
    private String operator;
    private String message;

    public Record() {
    }

    public Record(String genTime, String seqId, String type, String status, String p, String readerPointData, String site, String cardNumber, String account, String name, String operator, String message) {
        this.genTime = genTime;
        this.seqId = seqId;
        this.type = type;
        this.status = status;
        this.p = p;
        this.readerPointData = readerPointData;
        this.site = site;
        this.cardNumber = cardNumber;
        this.account = account;
        this.name = name;
        this.operator = operator;
        this.message = message;
    }

    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getReaderPointData() {
        return readerPointData;
    }

    public void setReaderPointData(String readerPointData) {
        this.readerPointData = readerPointData;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return genTime + " || " + readerPointData + " || " + site + " || " + cardNumber + " || " + name + " || " + "\n";
    }
}
