import React from "react";
import Event from "./Event";
import {shallow} from "enzyme";

describe('Event Component', () => {

  test('should load all component', () => {

    const component = shallow(
      <Event author='max'/>
    );

    expect(component.find('.event_window').length).toBe(1);
    expect(component.find('.event_list').length).toBe(1);
    expect(component.find('.event_form').length).toBe(1);

    expect(component.is('.event_window')).toBe(true);
  });
});

