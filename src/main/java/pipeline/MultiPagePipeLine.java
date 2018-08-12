package pipeline;

import java.io.IOException;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.PicUtils;

/**
 * 下载多个页面图片的PipeLine
 *
 * @author jrc
 */
public class MultiPagePipeLine implements Pipeline {

    private int number = 5; // 多个页面加起来的url数量(默认为5)
    private String basePath = "D:\\webmagic";

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public MultiPagePipeLine() {
    }

    public MultiPagePipeLine(int number, String basePath) {
        this.number = number;
        this.basePath = basePath;
    }


    public synchronized void process(ResultItems resultItems, Task task) {
        List<String> list = resultItems.get("urls");
        for (int i = 0; i < list.size(); i++) {
            String url = list.get(i);
            String filename = url.substring(url.lastIndexOf("/"));
            try {
                if (getNumber() < 0) {
                    System.exit(0);
                }
                if (PicUtils.download(basePath, filename, url)) {
                    number--;
                    setNumber(number);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

    }

}
