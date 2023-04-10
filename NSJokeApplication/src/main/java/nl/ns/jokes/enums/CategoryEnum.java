package nl.ns.jokes.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryEnum {
    ANY("/Any"),
    PROGRAMMING("/Programming"),
    MISC("/Misc"),
    DARK("/Dark"),
    PUN("/Pun"),
    SPOOKY("/Spooky"),
    CHRISTMAS("/Christmas");


    private final String urlParameterCategory;


}
