import React from 'react';
import App from './App';
import renderer from 'react-test-renderer';

describe('App Component', () => {

  test('should render login component', () => {
    localStorage.clear();

    const component = renderer.create(
      <App />
    );
    let tree = component.toJSON();
    expect(tree).toMatchSnapshot();

    expect(tree.props.className).toBe('login');

  });

  test('should ask author name from local storage', () => {
    localStorage.clear();

    renderer.create(
      <App />
    );

    expect(localStorage.getItem).toHaveBeenLastCalledWith('CHAT_AUTHOR');
  });
});

