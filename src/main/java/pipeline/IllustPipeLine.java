package pipeline;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class IllustPipeLine implements Pipeline {
    private List<String> urls = new ArrayList<>();
    private int number = 5;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public IllustPipeLine() {
    }

    public IllustPipeLine(int number) {
        this.number = number;
    }

    @Override
    public synchronized void process(ResultItems resultItems, Task task) {
        List<String> list = resultItems.get("urls");
        for (int i = 0; i < list.size(); i++) {
            String url = list.get(i);
            url = url.replace("240x480", "240x240");
            urls.add(url);
            number--;
            setNumber(number);
        }
        setUrls(urls);
        if (getNumber() < 0) {
            return;
        }
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
