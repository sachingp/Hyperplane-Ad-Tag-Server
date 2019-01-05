package com.ad.util.event;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sagupta
 *
 * |        1 | loaded   | media download           | |        2 | impression | media
 * impression/display | |        3 | click      | media click              | |        4 | conversion
 * | media conversion | |        5 | 25%        | video media 25%          | |        6 | 50% |
 * video media 50% | |        7 | 75%        | video media 75%          | |        8 | completion |
 * video media 100% | |        9 | install    | app install              | |       10 | ad request |
 * ad request
 */

@Getter
public enum EventEnum {

  LOADED(1), Impression(2), Click(3), Conversion(4), Video25(5), Video50(6), Video75(
      7), VideoCompletion(8), Install(9), AdRequest(10), ERROR(11);

  final int type;
  List<Integer> events = new ArrayList<>();

  EventEnum(int type) {
    this.type = type;
    events.add(this.type);
  }

  public List<Integer> allEvents() {
    return events;
  }

}
