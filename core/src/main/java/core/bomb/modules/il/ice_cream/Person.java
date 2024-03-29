package core.bomb.modules.il.ice_cream;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;

import static java.util.stream.Collectors.toCollection;

@SuppressWarnings("ConstantConditions")
public enum Person {
    MIKE, TIM, TOM, DAVE, ADAM, CHERYL, SEAN, ASHLEY, JESSICA, TAYLOR, SIMON, SALLY, JADE, SAM,
    GARY, VICTOR, GEORGE, JACOB, PAT, BOB;

    private static final String FILENAME;

    static {
        FILENAME = "allergyTable.csv";
    }

    public static EnumMap<Person, EnumSet<Allergen>> getPersonAllergens(int index) throws IllegalStateException {
        InputStream in = Person.class.getResourceAsStream(FILENAME);
        CSVReader reader = new CSVReader(new InputStreamReader(in));
        EnumMap<Person, EnumSet<Allergen>> output = new EnumMap<>(Person.class);
        Person[] people = values();
        int counter = 0;

        for (String[] line : reader) {
            output.put(people[counter++],
                    Arrays.stream(line[index].split(""))
                            .mapToInt(Integer::parseInt)
                            .mapToObj(Allergen::getByIndex)
                            .collect(toCollection(() -> EnumSet.noneOf(Allergen.class)))
            );
        }
        try {
            reader.close();
            return output;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
