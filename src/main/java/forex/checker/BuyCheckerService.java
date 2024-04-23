package forex.checker;

import forex.constant.Constant;
import forex.entity.Grid;
import forex.load.Price;
import forex.processing.GridService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BuyCheckerService {
    @Autowired
    private GridService gridService;
    private final List<Grid> grids = new ArrayList<>();
    @Autowired
    private M3CheckerService m3Checker;
    @Autowired
    private EMACheckerService emaChecker;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public List<Grid> getGrids() {
        return grids;
    }

    @SneakyThrows
    public void checkBuy(Grid grid, int i, List<Price> priceList, List<Price> priceListM3) {
        if (grid.getSizeGrid() >= 250
//                && grid.getSizeGrid() <= 600
//                && grid.getBuyPulseCount() >= 2
//                && grid.getBuyPulseCount() <= 90
//                && grid.getBuyDataValue().getHour() <= 17
//                && priceList.get(i - grid.getBuyRollbackCount()).getDateValue().getHour() >= 7
//                && grid.getBuyDataValue().getHour() >= 7 - (grid.getBuyRollbackCount() / 30)
//                && priceList.get(i - grid.getBuyRollbackCount()).getDateValue().getHour() <= 17
        ) {
//            count = 0;
            grids.add(grid);
            int m2Bar = Math.max(i - grid.getBuyPulseCount() - grid.getBuyRollbackCount() - 2, 0);
            LocalDateTime dateValue = priceList.get(m2Bar).getDateValue();
            int m3Position = getM3Position(priceListM3, m2Bar, dateValue);
            for (; m3Position < priceListM3.size(); m3Position++) {
                if (grid.getBuyMinGrid() == Integer.MAX_VALUE || priceListM3.get(m3Position).getDateValue().isAfter(dateValue.plusHours(1))) {
                    return;
                }
                if (priceListM3.get(m3Position).getMinPrice() == grid.getBuyMinGrid()) {
                    List<Price> m3 = priceListM3.subList(m3Position, m3Position + grid.getBuyPulseCount() + grid.getBuyRollbackCount());

//                    var c = executorService.submit(M3Checker.builder()
//                            .priceList(m3)
//                            .build());
//                    var d = executorService.submit(EMAChecker.builder()
//                            .m3Position(m3Position)
//                            .grid(grid)
//                            .priceList(priceListM3)
//                            .build());
//                    var v = executorService.submit(EMAChecker.builder()
//                            .m3Position(i)
//                            .grid(grid)
//                            .priceList(priceList)
//                            .build());
//
//                    grid.setM3Ok(c.get());
//                    grid.getEmaIntersect().setM3(d.get());
//                    grid.getEmaIntersect().setM2(v.get());

                    grid.setM3Ok(m3Checker.processM3(m3));
                    grid.getEmaIntersect().setM3(emaChecker.checkEma(m3Position, grid, priceListM3));
                    grid.getEmaIntersect().setM2(emaChecker.checkEma(i, grid, priceList));
                    if (grid.isM3Ok() && grid.getEmaIntersect().isM3() && grid.getEmaIntersect().isM2()) {
                        buyOpen(grid, priceList.get(i).getMinPrice());
                    }
                    break;
                }
            }
        }
    }

    private int getM3Position(List<Price> priceListM3, int m2Bar, LocalDateTime dateValue) {
        int m3Position = (m2Bar) * 2 / 3;
        int barsCountOffset = 1000;
        while (barsCountOffset > 2) {
            m3Position = getM3Position(priceListM3, dateValue, m3Position, barsCountOffset);
            barsCountOffset /= 2;
        }
        while (priceListM3.get(m3Position + 1).getDateValue().isBefore(dateValue.minusMinutes(10))) {
            m3Position += 1;
        }
        if (priceListM3.get(m3Position).getDateValue().isAfter(dateValue)) {
            throw new RuntimeException("fail");
        }
        return m3Position;
    }

    private int getM3Position(List<Price> priceListM3, LocalDateTime dateValue, int m3Position, int barsCountOffset) {
        while (priceListM3.size() > m3Position + barsCountOffset && priceListM3.get(m3Position + barsCountOffset).getDateValue().isBefore(dateValue)) {
            m3Position += barsCountOffset;
        }
        return m3Position;
    }

    private void buyOpen(Grid grid, float minPrice) {
        grid.getSteps().add(1);
        if ((grid.getBuyMaxGrid() - grid.getBuyMinGrid()) * Constant.FIBONACCI_0382 + grid.getBuyMinGrid() > minPrice) {
            grid.getSteps().add(6);
        }
        gridService.addWorkGrid(grid);
    }
}
