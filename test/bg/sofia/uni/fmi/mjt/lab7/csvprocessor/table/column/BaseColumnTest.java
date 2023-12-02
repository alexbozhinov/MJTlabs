package bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.column;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BaseColumnTest {

    private BaseColumn baseColumn;

    @BeforeEach
    public void setUp() {
        baseColumn = new BaseColumn();
    }

    @Test
    public void testAddDataCorrectData() {
        baseColumn.addData("Marcus Rashford");
        baseColumn.addData("Erling Haaland");
        baseColumn.addData("Marcus Rashford");

        Collection<String> columnData = new ArrayList<>(baseColumn.getData());

        assertEquals(Arrays.asList("Marcus Rashford", "Erling Haaland"), columnData);
    }

    @Test
    public void testAddDataNullDataValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> baseColumn.addData(null));
    }

    @Test
    public void testAddDataBlankDataValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> baseColumn.addData(""));
    }
}
