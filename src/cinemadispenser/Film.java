package cinemadispenser;

/**
 * @author octavio
 */
class Film implements Serializable{

    private String name;
    private String poster;
    private int duration;
    private String description;

    public Film(String name, String poster, int duration, String description) {
        this.name = name;
        this.poster = poster;
        this.duration = duration;
        this.description = description;
    }
    public String getName() {
        return name;
    }

    public String getPoster() {
        return poster;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Film{" + "name=" + name + ", poster=" + poster + ", duration=" + duration + ", description=" + description + '}';
    }   
}
