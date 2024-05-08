package ubb.proiect.MakeupSalon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class IntervalDto implements Serializable {
    private LocalDateTime start;
    private LocalDateTime end;

    @Override
    public String toString() {
        return "IntervalDto{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
