package ruiliu2.practice.elasticsearch.demo.entities;


import ruiliu2.practice.elasticsearch.annotations.HiseePSField;
import ruiliu2.practice.elasticsearch.annotations.HiseePSFieldIndex;
import ruiliu2.practice.elasticsearch.annotations.HiseePSFieldType;


/**
 * 转写结果item
 * Created by ruiliu2@iflytek.com on 16/5/1.
 */
public class Lattice {


    /**
     * 开始时间
     */
    @HiseePSField(type = HiseePSFieldType.Long)
    private long beginTime;
    /**
     * 结束时间
     */
    @HiseePSField(type = HiseePSFieldType.Long)
    private long endTime;
    /**
     * 引擎输出WS
     */
    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private String ws;
    /**
     * 消息类型  --progressive中间结果 --sentence最终结果
     */
    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private String msgType;
    /**
     * 角色
     */
    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private String role;
    /**
     * onebest内容
     */
    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private String onebestText;
    /**
     * lattice序号
     */
    @HiseePSField(type = HiseePSFieldType.Long, index = HiseePSFieldIndex.no)
    private long sequence;

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
     * role getter
     *
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * role setter
     *
     * @param role role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * onebestText getter
     *
     * @return onebestText
     */
    public String getOnebestText() {
        return onebestText;
    }

    /**
     * onebestText setter
     *
     * @param onebestText onebestText
     */
    public void setOnebestText(String onebestText) {
        this.onebestText = onebestText;
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
}
