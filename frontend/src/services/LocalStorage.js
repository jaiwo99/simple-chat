const KEY = 'CHAT_AUTHOR';

export default class LocalStorage {

  save(name) {
    localStorage.setItem(KEY, name);
  }

  read() {
    return localStorage.getItem(KEY);
  }
}
