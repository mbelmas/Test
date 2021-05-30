
import java.util.ArrayList;
import java.util.List;

    /**
     * Make this class immutable. See requirements in task description.
     */
    public final class Car {
        private final int year;
        private final String color;
        private  final List<Wheel> wheels;
        private final Engine engine;

        private Car(CarBuilder carBuilder) {
            this.year = carBuilder.year;
            this.color = carBuilder.color;
            this.wheels = new ArrayList<>(carBuilder.wheels);
            this.engine = (Engine) carBuilder.engine.clone();
        }

        public static class CarBuilder {
            private int year;
            private String color;
            private List<Wheel> wheels;
            private Engine engine;

            public CarBuilder setYear(int year) {
                this.year = year;
                return this;
            }

            public CarBuilder setColor(String color) {
                this.color = color;
                return this;
            }

            public CarBuilder setWheels(List<Wheel> wheels) {
                this.wheels = wheels;
                return this;
            }

            public CarBuilder setEngine(Engine engine) {
                this.engine = engine;
                return this;
            }
            public Car build() {
                return new Car(this);
            }
        }

    }
