import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import { JhipsterSampleApplicationAdminModule } from 'app/admin/admin.module';
import {
    UserTypeComponent,
    UserTypeDetailComponent,
    UserTypeUpdateComponent,
    UserTypeDeletePopupComponent,
    UserTypeDeleteDialogComponent,
    userTypeRoute,
    userTypePopupRoute
} from './';

const ENTITY_STATES = [...userTypeRoute, ...userTypePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, JhipsterSampleApplicationAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserTypeComponent,
        UserTypeDetailComponent,
        UserTypeUpdateComponent,
        UserTypeDeleteDialogComponent,
        UserTypeDeletePopupComponent
    ],
    entryComponents: [UserTypeComponent, UserTypeUpdateComponent, UserTypeDeleteDialogComponent, UserTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationUserTypeModule {}
