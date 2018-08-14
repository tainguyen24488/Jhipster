import { IUser } from 'app/core/user/user.model';

export interface IUserType {
    id?: number;
    type?: string;
    usertype?: IUser;
}

export class UserType implements IUserType {
    constructor(public id?: number, public type?: string, public usertype?: IUser) {}
}
