export class User {
  constructor(
    public id: string,
    public genres: string[],
    public role: string,

    public username: string,
    public email: string,
    public firstName: boolean,
    public lastName: string,
    public city: string,
    public country: string,
    public verified: boolean,
  ) {}
}
