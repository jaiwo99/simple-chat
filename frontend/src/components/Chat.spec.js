import React from "react";
import Chat from "./Chat";
import {shallow} from "enzyme";

describe('Chat Component', () => {

  test('should load all component', () => {

    const component = shallow(
      <Chat author='max'/>
    );

    expect(component.find('.chatlog').length).toBe(1);
    expect(component.find('.chatinput').length).toBe(1);

    expect(component.is('.chat')).toBe(true);
  });
});

