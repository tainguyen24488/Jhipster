/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UserTypeUpdateComponent } from 'app/entities/user-type/user-type-update.component';
import { UserTypeService } from 'app/entities/user-type/user-type.service';
import { UserType } from 'app/shared/model/user-type.model';

describe('Component Tests', () => {
    describe('UserType Management Update Component', () => {
        let comp: UserTypeUpdateComponent;
        let fixture: ComponentFixture<UserTypeUpdateComponent>;
        let service: UserTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [UserTypeUpdateComponent]
            })
                .overrideTemplate(UserTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UserType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.userType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
