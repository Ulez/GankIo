package comulez.github.gankio.data;

import java.util.List;

/**
 * Created by Ulez on 2016/9/2.
 * Email：lcy1532110757@gmail.com
 */
public class Content extends BaseData{
    private List<ResultsEntity> results;
    public List<ResultsEntity> getResults() {
        return results;
    }
    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }
}
