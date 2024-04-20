package forex.checker;

import forex.entity.SimpleGrid;
import forex.load.Price;
import forex.processing.GridService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Builder
public class M3CheckerService {

    private void resetSimpleGrid(SimpleGrid simpleGrid, float minGrid) {
        simpleGrid.setMinGrid(minGrid);
        simpleGrid.setPulseCount(1);
        simpleGrid.setRollbackCount(0);
        simpleGrid.setMaxGrid(0);
    }

    public boolean processM3(List<Price> priceList) {
        SimpleGrid simpleGrid = new SimpleGrid();
        return priceList.stream().anyMatch(x -> processGrid(simpleGrid, x));
    }

    private boolean processGrid(SimpleGrid grid, Price priceList) {
        boolean work = false;
        if (grid.getMinGrid() > priceList.getMinPrice()) { // create new min
            resetSimpleGrid(grid, priceList.getMinPrice());
        } else if (grid.getMaxGrid() < priceList.getMaxPrice() || (grid.getMaxGrid() == priceList.getMaxPrice() && grid.getRollbackCount() <= 1)) {  // create new max
            grid.setMaxGrid(priceList.getMaxPrice());
            grid.setPulseCount(grid.getPulseCount() + 1 + grid.getRollbackCount());
            grid.setRecLow(0);
            grid.setRollbackCount(0);
            grid.setMaxMax(false);
        } else if (grid.getMaxGrid() == priceList.getMaxPrice() && grid.getRollbackCount() > 1) {
            grid.setRollbackCount(grid.getRollbackCount() + 1);
            grid.setMaxMax(true);
            grid.setTempRec(grid.getRollbackCount());
        } else {
            grid.setRollbackCount(grid.getRollbackCount() + 1);
            if (grid.getRecLow() == 0) {
                grid.setRecLow(priceList.getMinPrice());
            } else if (grid.isMaxMax()) {
                grid.setPulseCount(grid.getPulseCount() + grid.getTempRec());
                grid.setRollbackCount(grid.getRollbackCount() - grid.getTempRec());
                grid.setMaxMax(false);
            }
        }
        if (grid.getRollbackCount() - grid.getPulseCount() == 0) {
            work = true;
        }
        return work;
    }

}
