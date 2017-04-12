package ruiliu2.practice.esrally.datagenerator;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ruiliu2
 * on 2017/3/23.
 */
public class Application {

    public static void main(String... args) throws IOException {
        RandomAccessFile outfile = new RandomAccessFile("/Users/casa/Study/personal/java-practice/esrally-datasource-generator/src/main/resources/document.json", "rw");
        for (int x = 0; x < 50; x++) {
            RandomAccessFile infile = new RandomAccessFile("/Users/casa/Study/personal/java-practice/esrally-datasource-generator/src/main/resources/honglongmeng.txt", "rw");
            FileChannel channel = infile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(20000);
            int bytesRead = 0;
            StringBuilder sb = new StringBuilder();
            List<Onebest> onebests = new ArrayList<Onebest>();
            for (int i = 0; i < 10; i++) {
                channel.read(byteBuffer);
                Onebest onebest = new Onebest();
                String content = new String(byteBuffer.array(), "GBK").replaceAll("\r\n", "").replaceAll(" ", "").replaceAll("\\\\", "");
                onebest.setContent(content);
                onebest.setBg(10);
                onebest.setEd(20);
                onebest.setId(UUID.randomUUID().toString());
                onebest.setSequence(1);
                sb.append(content);
                onebests.add(onebest);
                byteBuffer.flip();
            }
            SourceObject sourceObject = new SourceObject();
            sourceObject.setRes_id(UUID.randomUUID().toString());
            sourceObject.setContent(sb.toString());
            sourceObject.setJsons(onebests);

            outfile.write((JSON.toJSONString(sourceObject) + System.getProperty("line.separator")).getBytes("UTF-8"));
            infile.close();
        }
    }

    public static class SourceObject {
        private String res_id;
        private String content;
        private List<Onebest> jsons;

        /**
         * res_id getter
         *
         * @return res_id
         */
        public String getRes_id() {
            return res_id;
        }

        /**
         * res_id setter
         *
         * @param res_id res_id
         */
        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        /**
         * content getter
         *
         * @return content
         */
        public String getContent() {
            return content;
        }

        /**
         * content setter
         *
         * @param content content
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * jsons getter
         *
         * @return jsons
         */
        public List<Onebest> getJsons() {
            return jsons;
        }

        /**
         * jsons setter
         *
         * @param jsons jsons
         */
        public void setJsons(List<Onebest> jsons) {
            this.jsons = jsons;
        }
    }

    public static class Onebest {

        private String id;
        private int bg;
        private int ed;
        private String content;
        private int sequence;

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
         * bg getter
         *
         * @return bg
         */
        public int getBg() {
            return bg;
        }

        /**
         * bg setter
         *
         * @param bg bg
         */
        public void setBg(int bg) {
            this.bg = bg;
        }

        /**
         * ed getter
         *
         * @return ed
         */
        public int getEd() {
            return ed;
        }

        /**
         * ed setter
         *
         * @param ed ed
         */
        public void setEd(int ed) {
            this.ed = ed;
        }

        /**
         * content getter
         *
         * @return content
         */
        public String getContent() {
            return content;
        }

        /**
         * content setter
         *
         * @param content content
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * sequence getter
         *
         * @return sequence
         */
        public int getSequence() {
            return sequence;
        }

        /**
         * sequence setter
         *
         * @param sequence sequence
         */
        public void setSequence(int sequence) {
            this.sequence = sequence;
        }
    }


}
