import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TimetableCoachCountTest {

    private final Group group = new Group("Акробатика", Age.ADULT, 60);
    private final Coach coach1 = new Coach("Измайлов", "К", "В");
    private final Coach coach2 = new Coach("Семёнов", "В", "К");
    private final Coach coach3 = new Coach("Васильев", "Н", "С");

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();

        assertNotNull(counts);
        assertTrue(counts.isEmpty(), "Для пустого расписания список должен быть пустым");
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();

        assertEquals(1, counts.size(), "В списке должен быть ровно один тренер");
        assertEquals(coach1, counts.get(0).getCoach());
        assertEquals(2, counts.get(0).getCount(), "Количество тренировок должно быть равно 2");
    }

    @Test
    void testGetCountByCoachesMultipleCoachesSorting() {
        Timetable timetable = new Timetable();

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.THURSDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SATURDAY, new TimeOfDay(14, 0)));

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();

        assertEquals(3, counts.size(), "Должно вернуться 3 записи для трех тренеров");

        assertEquals(coach2, counts.get(0).getCoach());
        assertEquals(3, counts.get(0).getCount());

        assertEquals(coach3, counts.get(1).getCoach());
        assertEquals(2, counts.get(1).getCount());

        assertEquals(coach1, counts.get(2).getCoach());
        assertEquals(1, counts.get(2).getCount());
    }
}