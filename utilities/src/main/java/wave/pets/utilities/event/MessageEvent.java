package wave.pets.utilities.event;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import wave.pets.utilities.event.spi.Event;


@SuperBuilder
@NoArgsConstructor
public class MessageEvent extends Event {}
