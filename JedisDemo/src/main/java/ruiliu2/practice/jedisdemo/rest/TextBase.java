package ruiliu2.practice.jedisdemo.rest;

/**
 * 文本内容基类
 * Created by ruiliu2@iflytek.com on 2017/5/12.
 */
abstract class TextBase {

    /**
     * 开始时间
     */
    long beginTime;

    /**
     * 结束时间
     */
    long endTime;

    /**
     * onebest内容
     */
    String textContent;

    /**
     * lattice序号
     */
    long sequence;

    /**
     * 消息类型  --progressive中间结果 --sentence最终结果
     */
    String msgType;


    /**
     * beginTime getter
     *
     * @return beginTime
     */
    public long getBeginTime() {
        return beginTime;
    }

    /**
     * beginTime setter
     *
     * @param beginTime beginTime
     */
    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * endTime getter
     *
     * @return endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * endTime setter
     *
     * @param endTime endTime
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * textContent getter
     *
     * @return textContent
     */
    public String getTextContent() {
        return textContent;
    }

    /**
     * textContent setter
     *
     * @param textContent textContent
     */
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    /**
     * sequence getter
     *
     * @return sequence
     */
    public long getSequence() {
        return sequence;
    }

    /**
     * sequence setter
     *
     * @param sequence sequence
     */
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    /**
     * msgType getter
     *
     * @return msgType
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * msgType setter
     *
     * @param msgType msgType
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
