package ruiliu2.practice.jedisdemo.rest;


/**
 * 转写结果item
 * Created by ruiliu2@iflytek.com on 16/5/1.
 */
public class Lattice extends TextBase {

    /**
     * 引擎输出WS
     */
    private String ws;

    /**
     * 角色
     */
    private String voiceNumber;

    /**
     * 是否顺滑
     */
    private boolean smooth;

    /**
     * voiceNumber getter
     *
     * @return voiceNumber
     */
    public String getVoiceNumber() {
        return voiceNumber;
    }

    /**
     * voiceNumber setter
     *
     * @param voiceNumber voiceNumber
     */
    public void setVoiceNumber(String voiceNumber) {
        this.voiceNumber = voiceNumber;
    }


    /**
     * ws getter
     *
     * @return ws
     */
    public String getWs() {
        return ws;
    }

    /**
     * ws setter
     *
     * @param ws ws
     */
    public void setWs(String ws) {
        this.ws = ws;
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

    /**
     * smooth getter
     *
     * @return smooth
     */
    public boolean isSmooth() {
        return smooth;
    }

    /**
     * smooth setter
     *
     * @param smooth smooth
     */
    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
    }


    public Lattice() {
    }

    public Lattice(long beginTime, long endTime, String ws, String msgType, String textContent, boolean smooth, long sequence) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.ws = ws;
        this.msgType = msgType;
        this.textContent = textContent;
        this.smooth = smooth;
        this.sequence = sequence;
    }
}
