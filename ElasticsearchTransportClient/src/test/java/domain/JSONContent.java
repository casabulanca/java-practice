package domain;

import ruiliu2.practice.elasticsearch.annotations.HiseePSField;
import ruiliu2.practice.elasticsearch.annotations.HiseePSFieldIndex;
import ruiliu2.practice.elasticsearch.annotations.HiseePSFieldType;

/**
 * Created by ruiliu2 on 2017/4/14.
 */
public class JSONContent {
    /**
     * 开始时间
     */
    @HiseePSField(type = HiseePSFieldType.Long, index = HiseePSFieldIndex.no)
    private long beginTime;
    /**
     * 结束时间
     */
    @HiseePSField(type = HiseePSFieldType.Long, index = HiseePSFieldIndex.no)
    private long endTime;
    /**
     * 引擎输出WS
     */
    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private String ws;
    /**
     * lattice id
     */
    @HiseePSField(type = HiseePSFieldType.Keyword, index = HiseePSFieldIndex.no)
    private String id;
    /**
     * onebest内容
     */
    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private String onebestText;


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
     * id getter
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * id setter
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
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
}