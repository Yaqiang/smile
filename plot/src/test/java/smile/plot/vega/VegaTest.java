/*
 * Copyright (c) 2010-2021 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */

package smile.plot.vega;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Haifeng Li
 */
public class VegaTest {

    public VegaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBar() throws Exception {
        System.out.println("Bar");

        View bar = new View()
                .title("Simple Bar Plot")
                .description("A simple bar chart with embedded data.")
                .widthStep(30)
                .json("""
        [
          {"a": "A", "b": 28}, {"a": "B", "b": 55}, {"a": "C", "b": 43},
          {"a": "D", "b": 91}, {"a": "E", "b": 81}, {"a": "F", "b": 53},
          {"a": "G", "b": 19}, {"a": "H", "b": 87}, {"a": "I", "b": 52}
        ]""");

        bar.mark("bar");
        Field x = bar.encode("x", "a").type("ordinal");
        x.axis().labelAngle(0);
        bar.encode("y", "b").type("quantitative");
    }

    @Test
    public void testAggregateBar() throws Exception {
        System.out.println("Aggregate Bar");

        View bar = new View()
                .title("Aggregate Bar Plot")
                .description("A bar chart showing the US population distribution of age groups in 2000.")
                .heightStep(20)
                .data("https://vega.github.io/vega-lite/examples/data/population.json");

        bar.mark("bar");
        bar.transform().filter("datum.year == 2000");
        bar.encode("x", "people").type("quantitative").aggregate("sum").title("population");
        bar.encode("y", "age").type("ordinal");
        assertEquals("[{\"filter\":\"datum.year == 2000\"}]", bar.transform().toString());
    }

    @Test
    public void testSortedAggregateBar() throws Exception {
        System.out.println("Sorted Aggregate Bar");

        View bar = new View()
                .title("Sorted Aggregate Bar Plot")
                .description("A bar chart that sorts the y-values by the x-values.")
                .heightStep(20)
                .data("https://vega.github.io/vega-lite/examples/data/population.json");

        bar.mark("bar");
        bar.transform().filter("datum.year == 2000");
        bar.encode("x", "people").type("quantitative").aggregate("sum").title("population");
        bar.encode("y", "age").type("ordinal").sort("-x");
    }

    @Test
    public void testGroupBar() throws Exception {
        System.out.println("Group Bar");

        View bar = new View()
                .title("Group Bar Plot")
                .widthStep(12)
                .data("https://vega.github.io/vega-lite/examples/data/population.json");

        bar.mark("bar");
        bar.viewConfig().stroke("transparent").axis().domainWidth(1);
        bar.transform()
                .filter("datum.year == 2000")
                .calculate("datum.sex == 2 ? 'Female' : 'Male'", "gender");

        bar.encode("x", "gender").type("nominal").title(null);
        bar.encode("y", "people").type("quantitative").aggregate("sum").axis().title("population").grid(false);
        bar.encode("color", "gender").type("nominal").scaleRange("#675193", "#ca8861");
        bar.encode("column", "age").type("ordinal").spacing(10);
    }

    @Test
    public void testStackedBar() throws Exception {
        System.out.println("Stacked Bar");

        View bar = new View()
                .title("Stacked Bar Plot")
                .data("https://vega.github.io/vega-lite/examples/data/seattle-weather.csv");

        bar.mark("bar");
        bar.encode("x", "date").type("ordinal").timeUnit("month").title("Month of the year");
        bar.encode("y", null).type("quantitative").aggregate("count");
        bar.encode("color", "weather").type("nominal")
                .scaleDomain("sun", "fog", "drizzle", "rain", "snow")
                .scaleRange("#e7ba52", "#c7c7c7", "#aec7e8", "#1f77b4", "#9467bd")
                .legend().title("Weather type");
    }

    @Test
    public void testStackedBarWithRoundedCorner() throws Exception {
        System.out.println("Stacked Bar with Rounded Corner");

        View bar = new View()
                .title("Stacked Bar with Rounded Corner")
                .data("https://vega.github.io/vega-lite/examples/data/seattle-weather.csv");

        bar.mark("bar").cornerRadiusTopLeft(3).cornerRadiusTopRight(3);
        bar.encode("x", "date").type("ordinal").timeUnit("month").title("Month of the year");
        bar.encode("y", null).type("quantitative").aggregate("count");
        bar.encode("color", "weather").type("nominal");
        bar.show();
    }

    @Test
    public void testHorizontalStackedBar() throws Exception {
        System.out.println("Horizontal Stacked Bar");

        View bar = new View()
                .title("Horizontal Stacked Bar")
                .data("https://vega.github.io/vega-lite/examples/data/barley.json");

        bar.mark("bar");
        bar.encode("x", "yield").type("quantitative").aggregate("sum");
        bar.encode("y", "variety").type("nominal");
        bar.encode("color", "site").type("nominal");
        bar.show();
    }

    @Test
    public void testLayeredBar() throws Exception {
        System.out.println("Layered Bar");

        View bar = new View()
                .title("Layered Bar")
                .widthStep(17)
                .data("https://vega.github.io/vega-lite/examples/data/population.json");

        bar.mark("bar");
        bar.background().opacity(0.7);
        bar.transform()
                .filter("datum.year == 2000")
                .calculate("datum.sex == 2 ? 'Female' : 'Male'", "gender");

        bar.encode("x", "age").type("ordinal");
        bar.encode("y", "people").type("quantitative").aggregate("sum").title("population").stack(null);
        bar.encode("color", "gender").type("nominal").scaleRange("#675193", "#ca8861");

        bar.show();
    }
}
