import React from "react";
import LocalStorage from "../services/LocalStorage";
import renderer from "react-test-renderer";
import sinon from "sinon";
import Login from "./Login";

describe('Login Component', () => {

  test('should submit username', () => {
    localStorage.clear();

    const spy = sinon.spy();
    const store = new LocalStorage();
    const component = renderer.create(
      <Login store={store} reloadFn={spy}/>
    );
    let tree = component.toJSON();

    expect(tree).toMatchSnapshot();

    expect(localStorage.getItem('CHAT_AUTHOR')).toBeNull();

    tree.children[0].children[0].props.onChange({
      target: {
        value: 'max'
      },
      preventDefault: () => {
      }
    });

    tree.children[0].props.onSubmit({
      preventDefault: () => {
      }
    });

    tree = component.toJSON();
    expect(tree).toMatchSnapshot();

    expect(localStorage.getItem('CHAT_AUTHOR')).toBe('max');
    expect(spy.called).toBe(true);
  });
});

