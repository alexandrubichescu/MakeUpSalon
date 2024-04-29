package ubb.proiect.MakeupSalon.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Interval {
    private LocalDateTime start;
    private LocalDateTime end;
}
