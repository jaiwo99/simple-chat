import React from "react";
import Chat from "./Chat";
import {shallow} from "enzyme";

describe('Chat Component', () => {

  test('should load all component', () => {

    const component = shallow(
      <Chat author='max'/>
    );

    expect(component.find('.chat_head').length).toBe(1);
    expect(component.find('.chat_body').length).toBe(1);
    expect(component.find('.chat_foot').length).toBe(1);

    expect(component.is('.chat_window')).toBe(true);
  });
});

