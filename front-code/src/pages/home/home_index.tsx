/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

import {useSelector} from "react-redux";
import {WebInfoEntity} from "../../models/entity/web_info_entity.ts";
import {Link} from "react-router-dom";
import {
    Adjustments,
    Book,
    CommandLine,
    DocumentMagnifyingGlass,
    Eye,
    Forward,
    Github,
    Users
} from "../../assets/icon/index_svg.tsx";
import {LogoSVG} from "../../assets/icon/logo_svg.tsx";

import indexStart from "../../assets/images/index-start.webp";

/**
 * # 首页
 * 首页展示页面，展示网站的基本信息和功能介绍。
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
export function HomeIndex() {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    document.title = `${webInfo.name} - ${webInfo.description}`;

    return (
        <>
            <section className={"relative bg-cover bg-center bg-no-repeat " + "bg-[url(index-background.webp)]"}>
                <div
                    className="absolute inset-0 bg-gray-900/75 sm:bg-transparent sm:from-gray-900/95 sm:to-gray-900/25 sm:bg-gradient-to-r"/>
                <div
                    className="relative mx-auto max-w-screen-xl px-4 py-32 sm:px-6 lg:flex lg:h-screen lg:items-center lg:px-8">
                    <div className="max-w-xl text-center sm:text-left">
                        <h1 className="text-3xl font-extrabold text-sky-300 sm:text-5xl">
                            {webInfo.name}
                        </h1>
                        <p className="mt-4 max-w-lg text-white sm:text-xl/relaxed">
                            {webInfo.description}
                        </p>
                        <div className="mt-8 flex flex-wrap gap-4 text-center">
                            <Link to="/dashboard/home"
                                  className="transition block w-full rounded bg-teal-600 px-12 py-3 text-sm font-medium text-white shadow hover:bg-teal-700 focus:outline-none focus:ring active:bg-teal-500 sm:w-auto">
                                开始使用
                            </Link>
                            <Link to="#"
                                  className="transition block w-full rounded bg-white px-12 py-3 text-sm font-medium text-teal-500 shadow hover:text-teal-600 focus:outline-none focus:ring active:text-teal-800 sm:w-auto">
                                了解更多
                            </Link>
                        </div>
                    </div>
                </div>
            </section>

            <section className={"flex justify-center"}>
                <div className="max-w-screen-xl px-4 py-8 sm:px-6 sm:py-12 lg:px-8 lg:py-16">
                    <div className="grid grid-cols-1 gap-y-8 lg:grid-cols-2 lg:items-center lg:gap-x-16">
                        <div className="mx-auto max-w-lg text-center lg:mx-0 lg:text-left">
                            <h2 className="text-3xl font-bold sm:text-4xl">全面帮助</h2>
                            <p className="mt-4 text-gray-600">
                                学生日程规划平台旨在为学生提供一个全面、高效的日程管理工具，帮助他们合理规划学习与生活，提高时间管理能力。
                                通过该平台，学生可以轻松创建和管理个人日程、添加和查看课程表，同时与好友建立联系，互相查询彼此的空闲时间。
                                平台具备用户友好的界面和强大的功能，满足学生在学习期间的多样化需求，助力他们实现自我管理和高效学习。
                            </p>
                            <Link
                                to="/dashboard/home"
                                className="mt-8 inline-block rounded bg-teal-600 px-12 py-3 text-sm font-medium text-white transition hover:bg-teal-700 focus:outline-none focus:ring focus:ring-yellow-400"
                            >
                                现在开始使用
                            </Link>
                        </div>

                        <div className="grid grid-cols-2 gap-4 sm:grid-cols-3">
                            <div
                                className="transition block rounded-xl border border-gray-100 p-4 shadow-sm hover:border-gray-200 hover:ring-1 hover:ring-gray-200 focus:outline-none focus:ring">
                                <span className="inline-block rounded-lg bg-gray-50 p-3">
                                    <DocumentMagnifyingGlass/>
                                </span>
                                <h2 className="mt-2 font-bold">更快速</h2>
                                <p className="hidden sm:mt-1 sm:block sm:text-sm sm:text-gray-600">
                                    更快速地创建和管理个人日程，提高效率。
                                </p>
                            </div>
                            <div
                                className="transition block rounded-xl border border-gray-100 p-4 shadow-sm hover:border-gray-200 hover:ring-1 hover:ring-gray-200 focus:outline-none focus:ring">
                                <span className="inline-block rounded-lg bg-gray-50 p-3">
                                    <Forward/>
                                </span>
                                <h2 className="mt-2 font-bold">更高效</h2>
                                <p className="hidden sm:mt-1 sm:block sm:text-sm sm:text-gray-600">
                                    更高效地添加和查看课程表，提高学习效率。
                                </p>
                            </div>
                            <div
                                className="transition block rounded-xl border border-gray-100 p-4 shadow-sm hover:border-gray-200 hover:ring-1 hover:ring-gray-200 focus:outline-none focus:ring">
                                <span className="inline-block rounded-lg bg-gray-50 p-3">
                                    <Users/>
                                </span>
                                <h2 className="mt-2 font-bold">更便捷</h2>
                                <p className="hidden sm:mt-1 sm:block sm:text-sm sm:text-gray-600">
                                    更便捷地与好友建立联系，查询彼此的空闲。
                                </p>
                            </div>
                            <div
                                className="transition block rounded-xl border border-gray-100 p-4 shadow-sm hover:border-gray-200 hover:ring-1 hover:ring-gray-200 focus:outline-none focus:ring">
                                <span className="inline-block rounded-lg bg-gray-50 p-3">
                                    <CommandLine/>
                                </span>
                                <h2 className="mt-2 font-bold">更智能</h2>
                                <p className="hidden sm:mt-1 sm:block sm:text-sm sm:text-gray-600">
                                    智能化的安排工具，帮助合理规划时间。
                                </p>
                            </div>
                            <div
                                className="transition block rounded-xl border border-gray-100 p-4 shadow-sm hover:border-gray-200 hover:ring-1 hover:ring-gray-200 focus:outline-none focus:ring">
                                <span className="inline-block rounded-lg bg-gray-50 p-3">
                                    <Adjustments/>
                                </span>
                                <h2 className="mt-2 font-bold">更协作</h2>
                                <p className="hidden sm:mt-1 sm:block sm:text-sm sm:text-gray-600">
                                    提高团队沟通效率，促进协作。
                                </p>
                            </div>
                            <div
                                className="transition block rounded-xl border border-gray-100 p-4 shadow-sm hover:border-gray-200 hover:ring-1 hover:ring-gray-200 focus:outline-none focus:ring">
                                <span className="inline-block rounded-lg bg-gray-50 p-3">
                                    <Eye/>
                                </span>
                                <h2 className="mt-2 font-bold">更直观</h2>
                                <p className="hidden sm:mt-1 sm:block sm:text-sm sm:text-gray-600">
                                    可视化的时间安排，方便查看和协调时间。
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section className="overflow-hidden bg-gray-50 sm:grid sm:grid-cols-2 sm:items-center">
                <div className="p-8 md:p-12 lg:px-16 lg:py-24">
                    <div className="mx-auto max-w-xl text-center ltr:sm:text-left rtl:sm:text-right">
                        <h2 className="text-2xl font-bold text-gray-900 md:text-3xl">
                            立刻使用{webInfo.name}
                        </h2>
                        <p className="hidden text-gray-500 md:mt-4 md:block">
                            {webInfo.description}
                        </p>
                        <div className="mt-4 md:mt-8">
                            <Link
                                to="/dashboard/home"
                                className="inline-block rounded bg-teal-600 px-12 py-3 text-sm font-medium text-white transition hover:bg-teal-700 focus:outline-none focus:ring focus:ring-yellow-400"
                            >
                                Get Started Today
                            </Link>
                        </div>
                    </div>
                </div>
                <img
                    alt=""
                    src={indexStart}
                    className="h-full w-full object-cover sm:h-[calc(100%_-_2rem)] sm:self-end sm:rounded-ss-[30px] md:h-[calc(100%_-_4rem)] md:rounded-ss-[60px]"
                />
            </section>
            <footer className="bg-white">
                <div className="mx-auto max-w-screen-xl space-y-8 px-4 py-16 sm:px-6 lg:space-y-16 lg:px-8">
                    <div className="grid grid-cols-1 gap-8 lg:grid-cols-3">
                        <div>
                            <div className="text-teal-600">
                                <LogoSVG/>
                            </div>
                            <p className="mt-4 max-w-xs text-gray-500">
                                {webInfo.description}
                            </p>
                            <ul className="mt-8 flex gap-6">
                                <li>
                                    <Link
                                        to="https://github.com/XiaoLFeng/schedule-planning-platform"
                                        rel="noreferrer"
                                        target="_blank"
                                        className="text-gray-700 transition hover:opacity-75"
                                    >
                                        <span className="sr-only">GitHub</span>
                                        <Github/>
                                    </Link>
                                </li>
                                <li>
                                    <Link
                                        to="https://schedule-planning-platform.pages.dev/"
                                        rel="noreferrer"
                                        target="_blank"
                                        className="text-gray-700 transition hover:opacity-75"
                                    >
                                        <span className="sr-only">Document</span>
                                        <Book/>
                                    </Link>
                                </li>
                            </ul>
                        </div>

                        <div className="grid grid-cols-1 gap-8 sm:grid-cols-2 lg:col-span-2 lg:grid-cols-4">
                            <div>
                                <p className="font-medium text-gray-900">服务</p>
                                <ul className="mt-6 space-y-4 text-sm">
                                    <li>
                                        <Link to={""}
                                              className="text-gray-700 transition hover:opacity-75">课程表</Link>
                                    </li>
                                    <li>
                                        <Link to={""}
                                              className="text-gray-700 transition hover:opacity-75">日程</Link>
                                    </li>
                                </ul>
                            </div>

                            <div>
                                <p className="font-medium text-gray-900">作者</p>
                                <ul className="mt-6 space-y-4 text-sm">
                                    <li>
                                        <Link to={"https://blog.x-lf.com/"} target={"_blank"}
                                              className="text-gray-700 transition hover:opacity-75">
                                            博客
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to={"https://www.github.com/XiaoLFeng"} target={"_blank"}
                                              className="text-gray-700 transition hover:opacity-75">
                                            Github
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to={"https://space.bilibili.com/244321572"} target={"_blank"}
                                              className="text-gray-700 transition hover:opacity-75">
                                            哔哩哔哩
                                        </Link>
                                    </li>
                                </ul>
                            </div>
                            <div>
                                <p className="font-medium text-gray-900">帮助</p>
                                <ul className="mt-6 space-y-4 text-sm">
                                    <li>
                                        <Link to={"/help/faqs"}
                                              className="text-gray-700 transition hover:opacity-75">FAQs</Link>
                                    </li>
                                </ul>
                            </div>
                            <div>
                                <p className="font-medium text-gray-900">友链</p>
                                <ul className="mt-6 space-y-4 text-sm">
                                    <li>
                                        <Link to={"https://blog.x-lf.com/"} target={"_blank"}
                                              className="text-gray-700 transition hover:opacity-75">
                                            凌中的锋雨
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to={"https://img.akass.cn/"} target={"_blank"}
                                              className="text-gray-700 transition hover:opacity-75">
                                            晓白图床
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to={"https://tailwindcss.com/"} target={"_blank"}
                                              className="text-gray-700 transition hover:opacity-75">
                                            TailwindCSS
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to={"https://ant-design.antgroup.com/"} target={"_blank"}
                                              className="text-gray-700 transition hover:opacity-75">
                                            Ant Design
                                        </Link>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <p className="text-xs text-gray-500 grid">
                        <span>{webInfo.copyright} 版权所有.</span>
                        <Link className={"transition hover:opacity-75"} to={"https://beian.miit.gov.cn/"}
                              target={"_blank"}>{webInfo.icp}</Link>
                        <Link className={"transition hover:opacity-75"}
                              to={"https://beian.mps.gov.cn/#/query/webSearch"}
                              target={"_blank"}>{webInfo.record}</Link>
                    </p>
                </div>
            </footer>
        </>
    );
}
