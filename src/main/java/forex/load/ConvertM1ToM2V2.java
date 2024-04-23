package forex.load;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConvertM1ToM2V2 implements Callable<List<Price>> {
    @Autowired
    private ConvertM1ToM2 convertM1ToM2;
    @Setter
    private List<Price> priceM1List;

    @Override
    public List<Price> call() throws Exception {
        long startTime = System.currentTimeMillis();
        List<Price> prices = convertM1ToM2.convert(priceM1List);
        log.info("convert to M2 execution time is {} milliseconds", (System.currentTimeMillis() - startTime));
        return prices;
    }

}
